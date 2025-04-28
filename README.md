# backend_master

- Java Spring Server
- 스프링 서버


## 개발 환경
- windows10
- java IDE 17
- spring boot 3.5.0-SNAPSHOT


## 패키지 구성
- main 폴더 하위만 표기
- 도메인(주제) 기반 패키지 구성
- 도메인 별 3 depth 구성
- 도메인 하위에 요소들을 예시(유저)와 같이 나누어 작성
```
main
├─ java
│   └─ com.culturemoa.cultureMoaProject  -- 프로젝트 경로
│        ├─ common  -- 공통 코드 경로
│        ├─ user -- 유저 관련 코드
│        │    ├─ controller -- 유저 컨트롤러
│        │    ├─ dto -- 유저 dto
│        │    ├─ repository -- 유저 레포지토리(DAO 등)
│        │    └─ serevice -- 유저 서비스
│        ├─ CultureMoaProjectApplication -- 스프링부트 메인 실행 코드
│        └─ ServletInitializer
└─  resource
    ├─ static
    ├─ templates
    └─ application.properties -- 프로퍼티 파일
```

## 일정
- 2025.04.24 ~

## Review
- 
