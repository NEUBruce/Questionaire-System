# Questionnaire System (问卷调查系统)

A full-stack web application for creating and managing surveys/questionnaires. Users can organize questionnaires into projects, design questions from a template library, and collect/review responses.

## Tech Stack

- **Backend**: Spring Boot 3.1.0, Java 17, MyBatis 3.5.13
- **Database**: MySQL 8.0 (database: `neusql`, port 3306)
- **Frontend**: Vanilla HTML/CSS/JS with jQuery 3.5.1 + Bootstrap 3.4.1 (no build step)
- **Build**: Maven with JaCoCo coverage, SonarQube integration
- **Server port**: 8085

## Project Structure

```
src/main/java/com/sisp/
  controller/     REST API endpoints (5 controllers)
  service/        Business logic layer
  dao/            MyBatis mapper interfaces
  entity/         Domain model POJOs
  beans/          HttpResponseEntity response wrapper
  common/utils/   UUIDUtil for ID generation

src/main/resources/
  application.yml         Spring Boot config
  mapper/*.xml            MyBatis SQL mapper files
  static/pages/           Frontend HTML pages (one dir per page)
  static/utils/           Shared frontend JS utilities
  static/static/          Third-party assets (jQuery, Bootstrap, iconfont)
```

## Build & Run

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Tests
./mvnw test

# Code coverage report (generated in target/site/jacoco/)
./mvnw verify
```

Requires MySQL running locally with database `neusql`. Default credentials in `application.yml`: `root` / `lg20030408`.

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

All IDs are UUID strings. Audit fields on most tables: `created_by`, `creation_date`, `last_updated_by`, `last_update_date`. Questionnaire soft-deletes via `status = '3'`.

## API Conventions

- All endpoints: `POST`, `Content-Type: application/json`, `Accept: application/json`
- Response wrapper `HttpResponseEntity`:
  - `code: "666"` = success
  - `code: "0"` = failure
  - `message` = human-readable result
  - `data` = payload

### Endpoints

**User** (prefix `/admin`):
- `POST /admin/userLogin` — login
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
- `POST /queryQuestionnaireList` — list (filter by projectId, id, createdBy)
- `POST /modifyQuestionnaireInfo` — update
- `POST /deleteQuestionnaire` — soft delete (sets status='3')

**Question**:
- `POST /addQuestion` — add question to questionnaire
- `POST /queryQuestionList` — list questions for a questionnaire
- `POST /queryTemplateQuestionList` — list template questions (from shared library)
- `POST /searchTemplateQuestionList` — keyword search in template library

**Record**:
- `POST /addRecord` — submit a response (with nested AnswerEntity list)
- `POST /queryRecordList` — list submissions for a questionnaire

## Frontend Pages

Each page lives in `src/main/resources/static/pages/<name>/` with `index.html`, `index.js`, `index.css`.

| Page | Purpose |
|------|---------|
| `login` | Login screen |
| `questionnaire` | Main dashboard — list of questionnaires |
| `createQuestionnaire` | Choose to create from template or blank |
| `createNewQuestionnaire` | New questionnaire form |
| `designQuestionnaire` | Drag-and-drop questionnaire editor |
| `seeQuestionnaire` | View questionnaire detail / share link |
| `answerSheet` | Public survey fill-in page |
| `seeDetail` | View individual response detail |
| `templateQuestion` | Browse/search the shared question template library |
| `user` | Admin: list and manage users |
| `createUser` | Admin: create new user |
| `seeProject` | View project and its questionnaires |
| `createProject` | Create new project |
| `editProject` | Edit project info |
| `common/header` | Shared navigation component |

Shared frontend utilities (`static/utils/`):
- `storage.js` — localStorage wrapper (`$util.setItem` / `$util.getItem`)
- `app.js` — common init, header injection
- `index.js` — shared helpers

The API base URL is configured in `static/my-config.js` via `API_BASE_URL`. After login, user info is stored in localStorage under key `userInfo`.

## Key Implementation Notes

- `UUIDUtil` generates all entity IDs before insert — IDs are never DB-generated.
- `QuestionEntity.Order` field has a capital `O` (named `order` in DB but Java field is `Order`).
- Questions support a `leftTitle` field used for matrix-style questions.
- `QuestionnaireEntity` has `type`, `style`, `target`, `group` fields for categorization.
- `AnswerEntity.row` supports matrix questions where answers span multiple rows.
- Template questions are stored in a separate table (`question_template_info`) from live questions (`question_info`).
- The `deleteQuestionnaire` SQL actually does an UPDATE (soft delete), not a hard DELETE.
- No authentication middleware/session: the backend is stateless; user identity is passed via request body fields like `createdBy`.
