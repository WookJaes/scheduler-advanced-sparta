# Schedule App

Spring Boot와 JPA를 기반으로 일정 관리(CRUD)와 유저 관리, 그리고 세션 기반 로그인 기능을 제공하는 일정 관리 애플리케이션입니다.

## 개발 환경

![IntelliJ IDEA](https://img.shields.io/badge/Intellij%20Idea-000?logo=intellijidea&style=for-the-badge)
![Postman](https://img.shields.io/badge/Postman%2011.88.0-FF6C37?logo=postman&logoColor=white&style=for-the-badge)

![Java](https://img.shields.io/badge/Java%2017-ED8B00?logo=openjdk&style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%204.0.5-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)
![JPA](https://img.shields.io/badge/JPA%203.2.0-6DB33F?logo=hibernate&logoColor=white&style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL%208.4.8-4479A1?logo=mysql&logoColor=white&style=for-the-badge)

## API 명세서

Base URL: `http://localhost:8080`

### 로그인
| Description | Method | URL | Parameters | Request | Response | Status Code |
|------------|--------|----------|------------|--------------|----------|-------------|
| 로그인 | `POST` | `/users/login` | - | `{ "email": "abc@gmail.com", "password": "1q2w3e4r" }` | `{ "id": 1 }` | `200 OK`<br>`400 Bad Request` (요청 값 누락)<br>`401 Unauthorized` (이메일 또는 비밀번호 불일치) |
| 로그아웃 | `POST` | `/users/logout` | Session: `LOGIN_USER` | - | - | `204 No Content`<br>`400 Bad Request` (세션 만료) |
<br/>

### 사용자
| Description | Method | URL | Parameters | Request Body | Response | Status Code |
| --- | --- | --- | --- | --- | --- | --- |
| 회원가입 | `POST` | `/users/signup` | - | `{ "name": "홍길동", "email": "abc@gmail.com", "password": "1q2w3e4r" }` | `{ "id": 1, "name": "홍길동", "email": "abc@gmail.com" , "createdAt": , "modifiedAt": }` | `201 Created`<br>`400 Bad Request` (요청 값 누락)<br>`409 Conflict` (이미 존재하는 이메일) |
| 유저 목록 조회 | `GET` | `/users` | - | - | `[ { "id": 1, "name": "홍길동", "email": "abc@gmail.com", "createdAt": , "modifiedAt": }, ... ]` | `200 OK` |
| 단일 유저 조회 | `GET` | `/users/{userId}` | Path: `userId` | - | `{ "id": 1, "name": "홍길동", "email": "abc@gmail.com", "createdAt": , "modifiedAt": }` | `200 OK`<br>`404 Not Found` (존재하지 않는 사용자) |
| 회원 정보 수정 | `PUT` | `/users` | Session: `LOGIN_USER` | `{ "name": "홍길동", "password": "1q3e5t7u" }` | `{ "id": 1, "name": "홍길동", "email": "abc@gmail.com", "createdAt": , "modifiedAt": }` | `200 OK`<br>`400 Bad Request` (요청 값 오류)<br>`401 Unauthorized` (로그인 필요) |
| 회원 탈퇴 | `DELETE` | `/users` | Session: `LOGIN_USER` | `{ "email": "abc@gmail.com", "password": "1q3e5t7u" }` | - | `200 OK`<br>`400 Bad Request` (이메일 또는 비밀번호 불일치) |
<br/>

### 일정
| Description | Method | URL | Parameters | Request Body | Response | Status Code |
| --- | --- | --- | --- | --- | --- | --- |
| 일정 생성 | `POST` | `/schedules` | Session: `LOGIN_USER` | `{ "title": "일본 여행", "content": "9월 말 일본 여행" }` | `{ "id": 1, "title": "일본 여행", "content": "9월 말 일본 여행", "createdAt": , "modifiedAt": }` | `201 Created`<br>`400 Bad Request` (요청 값 누락)<br>`401 Unauthorized` (로그인 필요) |
| 단일 일정 조회 | `GET` | `/schedules/{scheduleId}` | Path: `scheduleId` | - | `{ "id": 1, "userId": 1, "title": "일본 여행", "content": "9월 말 일본 여행", "createdAt": , "modifiedAt": }` | `200 OK`<br>`404 Not Found` (존재하지 않는 일정) |
| 일정 수정 | `PUT` | `/schedules/{scheduleId}` | Path: `scheduleId`<br>Session: `LOGIN_USER` | `{ "title": "제주도 여행", "content": "8월 말 여행 계획" }` | `{ "id": 1, "title": "제주도 여행", "content": "8월 말 여행 계획", "createdAt": , "modifiedAt": }` | `200 OK`<br>`400 Bad Request` (요청 값 오류)<br>`401 Unauthorized` (로그인 필요)<br>`403 Forbidden` (작성자 아님)<br>`404 Not Found` (존재하지 않는 일정) |
| 일정 삭제 | `DELETE` | `/schedules/{scheduleId}` | Path: `scheduleId`<br>Session: `LOGIN_USER` | - | - | `200 OK`<br>`401 Unauthorized` (로그인 필요)<br>`403 Forbidden` (작성자 아님)<br>`404 Not Found` (존재하지 않는 일정) |
<br/>

## ERD
<img width="1852" height="436" alt="Image" src="https://github.com/user-attachments/assets/a4e0a057-41fb-4cd3-a43e-a889e126ca71" />
