# Questionnaire System (问卷调查系统)

A full-stack web application for creating and managing surveys/questionnaires. Users can organize questionnaires into projects, design questions, and collect/review responses.

## Tech Stack

- **Backend**: Spring Boot 3.1.0, Java 17, MyBatis 3.5.13, jjwt 0.11.5
- **Database**: MySQL 9.6.0 (database: `neusql`, port 3306)
- **Frontend**: Vue 3 + Vite + Vue Router 4 + Pinia + Element Plus + Axios (`frontend/`)
- **Legacy frontend**: Vanilla HTML/CSS/JS with jQuery (still in `src/main/resources/static/`, not actively used)
- **Build**: Maven (backend), npm (frontend)
- **Server ports**: backend 8085, frontend dev server 3001 (via `npm run dev`)

## Project Structure

```
frontend/                        Vue 3 SPA (front-end/back-end separation)
  src/
    api/         Axios API modules (request.js, user.js, project.js, questionnaire.js, question.js, record.js)
    components/  AppHeader.vue, QuestionItem.vue
    stores/      user.js (Pinia — userInfo + JWT token)
    views/       One .vue file per page (13 pages total)
    router/      index.js — Vue Router with auth guard

src/main/java/com/sisp/
  controller/          REST API endpoints (5 controllers)
  service/             Business logic layer
  dao/                 MyBatis mapper interfaces
  entity/              Domain model POJOs
  beans/               HttpResponseEntity response wrapper
  common/utils/        SnowflakeUtil (ID generation), JwtUtil (JWT sign/validate)
  common/interceptor/  JwtInterceptor (validates Bearer token on protected routes)
  common/config/       WebConfig (CORS + interceptor registration)

src/main/resources/
  application.yml      Spring Boot config (gitignored — use application.yml.example as template)
  mapper/*.xml         MyBatis SQL mapper files
  static/              Legacy jQuery frontend (not actively maintained)
```

## Build & Run

```bash
# Backend — requires Java 17
JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.17/libexec/openjdk.jdk/Contents/Home \
  mvn spring-boot:run

# Frontend dev server (proxies /api → http://localhost:8085)
cd frontend && npm run dev

# Frontend production build
cd frontend && npm run build
```

MySQL must be running: `brew services start mysql`  
Credentials are in `application.yml` (gitignored). Copy from `application.yml.example` and fill in password.

## Authentication (JWT)

- `POST /admin/userLogin` is public. On success it returns `{ user, token }` in `data`.
- All other endpoints require `Authorization: Bearer <token>` header.
- **Public exceptions** (no token needed): `/queryQuestionnaireList`, `/addRecord`, `/queryRecordList` — required for the public answer sheet page.
- Token is signed with HS256, expires in 7 days. Logic in `JwtUtil.java`.
- Frontend stores token in `localStorage['token']` and attaches it via Axios request interceptor. On 401, localStorage is cleared and user is redirected to `/login`.
- Authenticated users visiting `/login` are automatically redirected to `/questionnaire`.

## Data Model

| Entity | Table | Description |
|--------|-------|-------------|
| User | `user_info` | System users with username/password, status, valid date range |
| Project | `project_info` | Top-level container owned by a user |
| Questionnaire | `questionnaire_info` | Survey form within a project; has type, style, target, group, time limit |
| Question | `question_info` | Individual question within a questionnaire, ordered |
| Option | `option_info` | Answer options for choice questions |
| Record | `record_info` | One submission (who answered, when) |
| Answer | `answer_info` | Individual answer within a record |
| Question Template | `question_template_info` | Shared question library for reuse |

All IDs are Snowflake-generated strings (replaced UUID). Audit fields on most tables: `created_by`, `creation_date`, `last_updated_by`, `last_update_date`. Questionnaire soft-deletes via `status = '3'`.

## API Conventions

- All endpoints: `POST`, `Content-Type: application/json`, `Accept: application/json`
- Response wrapper `HttpResponseEntity`: `code: "666"` = success, `code: "0"` = failure
- Protected endpoints require `Authorization: Bearer <token>` header (see Authentication above)

### Endpoints

**User** (prefix `/admin`):
- `POST /admin/userLogin` — login → returns `{ user, token }` *(public)*
- `POST /admin/queryUserList` — list users
- `POST /admin/addUser` — create user
- `POST /admin/deleteUserinfo` — delete user
- `POST /admin/modifyUserInfo` — update user

**Project**:
- `POST /queryProjectList` — list by projectName or userId
- `POST /addProjectInfo` — create project
- `POST /modifyProjectInfo` — update project
- `POST /deleteProjectById` — delete project

**Questionnaire**:
- `POST /addQuestionnaire` — create
- `POST /queryQuestionnaireList` — list (filter by projectId, id, createdBy) *(public)*
- `POST /modifyQuestionnaireInfo` — update
- `POST /deleteQuestionnaire` — soft delete (sets status='3')

**Question**:
- `POST /addQuestion` — add question to questionnaire
- `POST /queryQuestionList` — list questions for a questionnaire
- `POST /queryTemplateQuestionList` — list template questions
- `POST /searchTemplateQuestionList` — keyword search in template library

**Record**:
- `POST /addRecord` — submit a response *(public)*
- `POST /queryRecordList` — list submissions *(public)*

## Frontend Pages (Vue)

| Route | View | Auth | Purpose |
|-------|------|------|---------|
| `/login` | LoginView | No | Login |
| `/questionnaire` | QuestionnaireView | Yes | Project list + nested questionnaires |
| `/create-project` | ProjectFormView | Yes | Create project |
| `/edit-project` | ProjectFormView | Yes | Edit project (detected by `route.query.id`) |
| `/see-project` | SeeProjectView | Yes | Project detail + questionnaire management |
| `/create-questionnaire` | CreateQuestionnaireView | Yes | Choose creation method |
| `/create-new-questionnaire` | QuestionnaireFormView | Yes | New questionnaire form |
| `/design-questionnaire` | DesignQuestionnaireView | Yes | Question editor (sidebar + canvas) |
| `/see-questionnaire` | SeeQuestionnaireView | Yes | Response list for a project |
| `/see-detail` | SeeDetailView | Yes | Single response detail (read-only) |
| `/user` | UserView | Yes | User management |
| `/create-user` | UserFormView | Yes | Create/edit user |
| `/answer-sheet` | AnswerSheetView | No | Public survey fill-in page |

## Key Implementation Notes

- `SnowflakeUtil` (workerId=1, datacenterId=1) generates all entity IDs — never DB-generated.
- `QuestionEntity.Order` field has a capital `O` (Java naming quirk matching DB column `order`).
- `QuestionItem.vue` handles all 5 question types (single/multi/fill/matrix/scale) with a `readonly` prop used by SeeDetailView.
- Matrix questions use `leftTitle` (comma-separated row labels) + `option` list for columns.
- Vite proxy: `/api/*` → `http://localhost:8085/*` (strips `/api` prefix).
- The `deleteQuestionnaire` SQL does an UPDATE (soft delete), not a hard DELETE.
