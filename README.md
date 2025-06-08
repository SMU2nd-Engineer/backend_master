초보자도 쉽게 이해할 수 있도록 구성하고, 배포와 실행 방법까지 포함했습니다.

# 🛠️ Fanit Backend

팬과 팬을 연결하는 커뮤니티 플랫폼 **Fanit**의 백엔드 서버입니다.  
Spring Boot 기반 RESTful API 서버로, 사용자 인증, 게시판, 채팅 기능 등을 제공합니다.

---

## 📌 주요 기능

- 사용자 회원가입 및 로그인 (JWT 인증)
- 게시글 CRUD (Quill 기반 에디터 지원)
- 1:1 실시간 채팅 기능 (WebSocket)
- 이미지 업로드 및 S3 연동
- MongoDB를 활용한 채팅 내역 저장

---

## ⚙️ 기술 스택

| 분류       | 기술                                               |
|------------|----------------------------------------------------|
| Language   | Java 8                                             |
| Framework  | Spring Boot, Spring Security, WebSocket           |
| DB         | MySQL, MongoDB Atlas                              |
| Infra      | AWS EC2, Docker, S3, Nginx                         |
| Others     | JPA, JWT, Gradle, Logback, Validation              |

---

## 📁 프로젝트 구조
도메인 기반 패키지 구조

```bash
📦src
 ┣ 📂main
 ┃ ┣ 📂java/com/cultureMoaProject
 ┃ ┃ ┣ 📂common        
 ┃ ┃ ┣ 📂...      
 ┃ ┃ ┣ 📂...      
 ┃ ┃ ┣ 📂...     
 ┃ ┃ ┗ 📜Application.java
 ┣ 📂resources
 ┃ ┣ 📜application.properties         # 공통 설정 (포트, 로깅 레벨 등)
 ┃ ┣ 📜application-dev.properties     # 로컬 개발 환경에 맞춘 DB, S3, WebSocket 등 세부 설정
 ┃ ┗ 📜application-docker.properties  # 운영 서버(Docker 기반)에서 사용할 외부 IP, 인증, S3 연결 등의 설정 포함
```
## 📌 패키지 구성 설명
- **common**: 프로젝트 전역에서 사용되는 설정 및 유틸리티 모듈
  - `config`: Spring 설정, WebConfig 등 포함
  - `controller`: 공통 예외 처리, 응답 래핑 등
  - `jwt`: JWT 생성 및 인증 필터 관련 클래스
  - `security`: Spring Security 설정 (필터 체인, 커스텀 인증 등)
  - `service.S3Service`: AWS S3 이미지 업로드, 삭제 처리
- **board**: 게시판 CRUD, 댓글 등 게시판 중심 기능 담당
- **category**: 분류 및 필터링을 위한 카테고리 관련 로직
- **chat**: 1:1 채팅, WebSocket 처리, MongoDB 연동
- **log**: 사용자 및 관리자 활동 로그 기록용 모듈
- **payment**: 결제 요청, 결제 결과 저장 등 처리
- **product**: 상품 정보 등록 및 수정 관리
- **search**: 키워드 기반 검색 등 구현
- **ticket**: 티켓 확인 및 상태 관리
- **user**: 회원가입, 로그인, 마이페이지 등 사용자 인증 및 관리 기능
---
## 🧩 특징

- 도메인 중심 설계로 유지보수성과 확장성 고려
- JWT + Spring Security 기반 인증 처리
- AWS S3 및 MongoDB와의 통합 구조 포함

## 🐳 개발 및 실행 방법
# 1. 환경 설정
환경 변수 (.properties)
- `application.properties`: 공통 설정 (포트, 로깅 레벨 등)
- `application-dev.properties`: 로컬 개발 환경에 맞춘 DB, S3, WebSocket 등 세부 설정
- `application-docker.properties`: 운영 서버(Docker 기반)에서 사용할 외부 IP, 인증, S3 연결 등의 설정 포함
```
# application.properties
spring.profiles.active=dev
```
```
# application-dev.properties
JWT_SECRET_KEY=your-secret-key
AWS_S3_BUCKET=your-bucket-name
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
MONGO_URI=your-mongo-uri
MYSQL_URL=jdbc:mysql://localhost:3306/
MYSQL_USERNAME=your-db-user
MYSQL_PASSWORD=your-db-password
```
```
# application-docker.properties
JWT_SECRET_KEY=your-secret-key
AWS_S3_BUCKET=your-bucket-name
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
MONGO_URI=your-mongo-uri
MYSQL_URL=jdbc:mysql://your-container:3306/
MYSQL_USERNAME=your-db-user
MYSQL_PASSWORD=your-db-password
```
# 2. 실행 방법 (Docker)
로컬 MySQL 및 MongoDB 실행 후
```
docker-compose build --no-cache
docker run -d \
  --name springboot-app \
  --network culturemoa-network \
  -p 8100:8100 \
  springboot-app:latest
```

# 🔐 보안 및 인증
Spring Security 기반 로그인/회원가입 API

# JWT 토큰 기반 인증 방식

WebSocket 인증은 HTTP Handshake 시 JWT 토큰 검증 처리

# 🗃️ DB 설계
MySQL: 사용자, 게시글, 댓글 등 구조화된 데이터 저장

MongoDB: 채팅 메시지 및 로그 저장

# 🧪 테스트
Postman을 통해 API 시나리오 테스트 수행

인증 토큰이 필요한 API는 Bearer Token 설정 필요

# 📌 향후 개선 방향
알림 기능 FCM 연동

Redis 기반 실시간 처리 개선

채팅방 사용자 상태 관리 기능 고도화

# 📬 Contact
문의: hankm9807@gmail.com
