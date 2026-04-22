# Scheduler App API

Spring Boot와 JPA를 기반으로 일정 및 유저 관리 기능과 세션 기반 인증을 제공하는 API 프로젝트입니다.
<br/><br/>

## 개발 환경

![IntelliJ IDEA](https://img.shields.io/badge/Intellij%20IDEA-000?logo=intellijidea&style=for-the-badge)
![Postman](https://img.shields.io/badge/Postman%2011.88.0-FF6C37?logo=postman&logoColor=white&style=for-the-badge)

![Java](https://img.shields.io/badge/Java%2017-ED8B00?logo=openjdk&style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%204.0.5-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)
![JPA](https://img.shields.io/badge/JPA%203.2.0-6DB33F?logo=hibernate&logoColor=white&style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL%208.4.8-4479A1?logo=mysql&logoColor=white&style=for-the-badge)

<br/>

## 주요 기능

- 유저 생성, 전체 조회, 단건 조회, 수정, 삭제
- 로그인 및 세션 기반 인증/인가 처리
- 일정 생성, 전체 조회, 단건 조회, 수정, 삭제
- 댓글 생성, 목록 조회, 수정, 삭제
- 일정 목록 페이징 조회 (Pageable 기반)
- 일정 조회 시 댓글 수 및 작성자 이름 포함
- 입력값 유효성 검증 (Validation 적용)
- 전역 예외 처리 및 공통 응답 구조 적용
<br/>

## API 명세서

Base URL: `http://localhost:8080`
<br/>

### 로그인
| Description | Method | URL | Parameters | Request | Response | Status Code |
|------------|--------|----------|------------|--------------|----------|-------------|
| 로그인 | `POST` | `/users/login` | - | `{ "email": "abc@gmail.com", "password": "1q2w3e4r" }` | - | `200 OK`<br>`400 Bad Request`<br>`401 Unauthorized` |
| 로그아웃 | `POST` | `/users/logout` | Session: `LOGIN_USER` | - | - | `204 No Content`<br>`401 Unauthorized` |
<br/>

### 사용자
| Description | Method | URL | Parameters | Request | Response | Status Code |
| --- | --- | --- | --- | --- | --- | --- |
| 회원가입 | `POST` | `/users/signup` | - | `{ "name": "김철수", "email": "abc@gmail.com", "password": "1q2w3e4r" }` | `{ "id": 1, "name": "김철수", "email": "abc@gmail.com", "createdAt": "2026-04-22T14:22:45.769935", "modifiedAt": "2026-04-22T14:22:45.769935" }` | `201 Created`<br>`400 Bad Request`<br>`409 Conflict` |
| 유저 목록 조회 | `GET` | `/users` | - | - | `[ { "id": 1, "name": "김철수", "email": "abc@gmail.com", "createdAt": "2026-04-22T14:22:45.769936", "modifiedAt": "2026-04-22T14:22:45.769936" } ]` | `200 OK` |
| 유저 단건 조회 | `GET` | `/users/{userId}` | Path: `userId` | - | `{ "id": 1, "name": "김철수", "email": "abc@gmail.com", "createdAt": "2026-04-22T14:22:45.769936", "modifiedAt": "2026-04-22T14:22:45.769936" }` | `200 OK`<br>`404 Not Found` |
| 회원 정보 수정 | `PUT` | `/users` | Session: `LOGIN_USER` | `{ "name": "김민수", "password": "1q2w3e4r" }` | `{ "id": 1, "name": "김민수", "email": "abc@gmail.com", "createdAt": "2026-04-22T14:22:45.769936", "modifiedAt": "2026-04-22T14:22:45.769936" }` | `200 OK`<br>`400 Bad Request`<br>`401 Unauthorized` |
| 회원 탈퇴 | `DELETE` | `/users` | Session: `LOGIN_USER` | `{ "email": "abc@gmail.com", "password": "1q2w3e4r" }` | - | `204 No Content`<br>`400 Bad Request`<br>`401 Unauthorized` |
<br/>

### 일정
| Description | Method | URL | Parameters | Request | Response | Status Code |
| --- | --- | --- | --- | --- | --- | --- |
| 일정 생성 | `POST` | `/schedules` | Session: `LOGIN_USER` | `{ "title": "일본 여행", "content": "9월 말 일본 여행" }` | `{ "id": 1, "title": "일본 여행", "content": "9월 말 일본 여행", "createdAt": "2026-04-22T14:28:08.2026219", "modifiedAt": "2026-04-22T14:28:08.2026219" }` | `201 Created`<br>`400 Bad Request`<br>`401 Unauthorized` |
| 전체 일정 조회 | `GET` | `/schedules` | Query: `page`, `size` | - | `{ "page": 0, "size": 10, "totalElements": 1, "totalPages": 1, "content": [ { "id": 1, "title": "일본 여행", "content": "9월 말 일본 여행", "commentCount": 0, "createdAt": "2026-04-22T14:28:08.202622", "modifiedAt": "2026-04-22T14:28:08.202622", "userName": "김민수" } ] }` | `200 OK` |
| 단건 일정 조회 | `GET` | `/schedules/{scheduleId}` | Path: `scheduleId` | - | `{ "id": 1, "userId": 1, "title": "일본 여행", "content": "9월 말 일본 여행", "createdAt": "2026-04-22T14:28:08.202622", "modifiedAt": "2026-04-22T14:28:08.202622" }` | `200 OK`<br>`404 Not Found` |
| 일정 수정 | `PUT` | `/schedules/{scheduleId}` | Path: `scheduleId`<br>Session: `LOGIN_USER` | `{ "userId": 1, "title": "제주도 여행", "content": "7월 말 제주도 여행 일정" }` | `{ "id": 1, "title": "제주도 여행", "content": "7월 말 제주도 여행 일정", "createdAt": "2026-04-22T14:28:08.202622", "modifiedAt": "2026-04-22T14:36:57.698263" }` | `200 OK`<br>`400 Bad Request`<br>`401 Unauthorized`<br>`403 Forbidden`<br>`404 Not Found` |
| 일정 삭제 | `DELETE` | `/schedules/{scheduleId}` | Path: `scheduleId`<br>Session: `LOGIN_USER` | - | - | `204 No Content`<br>`401 Unauthorized` <br>`403 Forbidden`<br>`404 Not Found` |
<br/>

### 댓글

| Description | Method | URL | Parameters | Request | Response | Status Code |
|-------------|--------|-----|------------|---------|----------|-------------|
| 댓글 생성 | `POST` | `/schedules/{scheduleId}/comments` | Path:<br>`scheduleId` <br>Session:<br>`LOGIN_USER` | `{ "content": "저도 같이 가고 싶어요." }` | `{ "id": 1, "content": "저도 같이 가고 싶어요.", "userId": 1, "scheduleId": 1, "createdAt": "2026-04-22T14:39:35.3252962", "modifiedAt": "2026-04-22T14:39:35.3252962" }` | `201 Created`<br>`400 Bad Request`<br>`401 Unauthorized`<br>`404 Not Found` |
| 댓글 조회 | `GET` | `/schedules/{scheduleId}/comments` | Path:<br>`scheduleId` | - | `[ { "id": 1, "content": "저도 같이 가고 싶어요.", "userId": 1, "scheduleId": 1, "createdAt": "2026-04-22T14:39:35.325296", "modifiedAt": "2026-04-22T14:39:35.325296" } ]` | `200 OK` |
| 댓글 수정 | `PATCH` | `/schedules/{scheduleId}/comments/{commentId}` | Path: `scheduleId`, `commentId`<br>Session: `LOGIN_USER` | `{ "content": "일정이 생겨서 저는 PASS" }` | `{ "id": 1, "content": "일정이 생겨서 저는 PASS", "userId": 1, "scheduleId": 1, "createdAt": "2026-04-22T14:39:35.325296", "modifiedAt": "2026-04-22T14:39:35.325296" }` | `200 OK`<br>`400 Bad Request` <br>`401 Unauthorized`<br>`403 Forbidden`<br>`404 Not Found` |
| 댓글 삭제 | `DELETE` | `/schedules/{scheduleId}/comments/{commentId}` | Path: `scheduleId`, `commentId`<br>Session: `LOGIN_USER` | - | - | `204 No Content`<br>`400 Bad Request`<br>`401 Unauthorized` <br>`403 Forbidden`<br>`404 Not Found` |

<br/>

## ERD
<img width="1245" height="1111" alt="Image" src="https://github.com/user-attachments/assets/c4d73897-cfec-48fc-a161-84b61a4c2c80" />

<br/>

## 프로젝트 구조

```text
src/main/java/com/wookjae/scheduler
├─comment
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
├─global
│  ├─auth
│  ├─config
│  ├─entity
│  └─exception
├─schedule
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
├─user
│   ├─controller
│   ├─dto
│   ├─entity
│   ├─repository
│   └─service
└─ SchedulerAdvancedApplication.java
```
- **controller**: 클라이언트 요청/응답 처리
- **service**: 비즈니스 로직 처리 (CRUD)
- **repository**: 데이터 접근 (JPA)
- **entity**: DB와 매핑되는 도메인 객체
- **dto**: 요청/응답 데이터 전달 객체
- **global**: 전역 공통 기능 관리

  - **auth**: 로그인(세션) 검증
  
  - **config**: 비밀번호 암호화 설정 (PasswordEncoder)
  
  - **entity**: 공통 엔티티 (Auditing: 생성/수정 시간)
  
  - **exception**: 공통 예외 처리
