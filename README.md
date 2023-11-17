# wal-backend


## 기술 스택
- 언어 및 프레임워크 : `Java`, `Spring Framework`, `Spring Boot`
- 데이터베이스 : `MySQL`
- ORM : `JPA`
- CI/CD : `github actions`, `AWS S3`, 
- 배포 환경 : `AWS EC2`, `Nginx`

<br>

## 아키텍처 및 폴더링

- [프로젝트에 적용한 아키텍처](https://cozo.tistory.com/6)

<br>

## 문제 해결
- [메모리 부족으로 인한 Hikari Pool thread starvation 문제 해결](https://shadow-lace-9f0.notion.site/Hikari-Pool-Thread-starvation-or-clock-leap-detected-b75a6317506a4c4f83146f5e44ca3a78?pvs=4)
- [RabbitMQ 도입 실패와 ScheduledExecutorService 를 이용한 예약 알림 시스템 구현](https://shadow-lace-9f0.notion.site/ScheduledExecutorService-07ed4d06a40c439ea73a54b82139d32b?pvs=4)
- [FCM 서버 에러 및 네트워크 불량으로 인한 전송 누락 문제 해결](https://shadow-lace-9f0.notion.site/FCM-bf8f5eddb6134531b477040567804c18?pvs=4)

<br>


## 기능 및 개발 

<br>

## 개발 API 목록 
|기능|endpoints|
|:-:|:-:|
|로그인 및 회원가입|`/auth/login`|
|refresh token 재발급|`/auth/reissue`|
|온보딩 정보 설정|`/onboard`|
|온보딩 알림 시간대 편집|`/onboard/time/edit`|
|온보딩 카테고리 편집|`/onboard/category/edit`|
|메인 페이지|`/today-wal`|
|확인한 왈소리 상태 변경|`/today-wal/{todayWalId}`|
|예약 왈소리 등록|`/reservation/register`|
|예약 왈소리 취소|`/reservation/{reservationId}/cancel`|
|예약 왈소리 히스토리 조회|`/reservation/history`|
|예약 왈소리 히스토리 삭제|`/reservation/history/{reservationId}/remove`|
|예약 왈소리 달력에서 조회|`/reservation/calender`|
|마이페이지 닉네임 조회|`/user/me/nickname`|
|마이페이지 알림 시간대 조회|`/user/me/time`|
|마이페이지 카테고리 조회|`/user/me/category`|
|마이페이지 닉네임 변경|`/user/me/nickname/edit`|
|마이페이지 회원 탈퇴|`/user/me/resign`|


