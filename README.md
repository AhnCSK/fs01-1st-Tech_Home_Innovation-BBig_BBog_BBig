# 주제 : 스마트 홈 제어 시스템 구축

JDBC와 파이썬을 이용한 라즈베리파이 구축 및 MQTT통신

JDBC를 포함한 자바 소스코드 전체 : https://github.com/geonwoo1226/fs01-1st-Tech_Home_Innovation-BBig_BBog_BBig.git

라즈베리파이 관련 파이썬 소스코드 전체 : https://github.com/geonwoo1226/NOVA_first_project.git

# 테크 홈 이노베이션 - 삑뽁삑(BBig BBog BBig)

> 아파트 정보/입주민 게시판조회가능한 실시간 통합 안전관리 스마트홈 시스템
> 
> 
> 자바/라즈베리파이의 통신을 통한 아파트 홈 제어앱 프로젝트로 라즈베리파이 센서의 데이터를 중심으로, 전등 관리·LED ON/OFF, 커튼조작 서보모터 (ON/OFF), 부저경고음, 온/습도센서를 이용한 화재 감지, 인체 감지 센서를 통한 외출 중 침입자 감지 (사용자의 외출 설정 필요)
> 

---

## 🎥 1. 프로젝트 시연 영상

[![영상 보기](https://github.com/user-attachments/assets/37c76f79-ed89-4c6e-be3a-0ba4e540d0ed)](https://youtu.be/f0WIfpXKhSc?si=OiSU5ED7FPzZP7mc)

---

## 📘 2. 프로젝트 개요

| 항목 | 내용 |
| --- | --- |
| **팀명** | 테크 홈 이노베이션 |
| **프로젝트명** | 삑뽁삑(BBig BBog BBig) |
| **목표** | 라즈베리파이 센서 입력 데이터를 통합해 **내 아파트 정보/ 마트 물품구입 / 입주민 게시판 조회** 등을 콘솔로 제공 |
| **주요성과** | 입주민 커뮤니티 게시판, 마트 물품구입 요약 집계, 아파트 호수별 실시간 정보 콘솔 시각화 구축 |
| **기간** | 2025.10.21 – 2025.10.29 |
| **사용 센서 목록<br>(18)** | LED(6), Buzzer(2), Button(3),  9G servo(2), PIR 센서(HC-SR501)(1), 초음파센서(Ultrasonic ranging)(1), LCD1602 디스플레이(2), 온습도센서(DHT11)(1), 미니 워터펌프(1), 2채널 모터드라이버(L9110S)(1), RFID리더기(RC522)(1) |
| **산출물** | 라즈베리파이 구현물 ,Java 앱, 데이터베이스 , PPT, README 문서 ,시연영상 |

---

## 👥 3.팀원 및 역할

---

| 이름 | 역할 및 담당 업무 | 개인 Git |
| --- | --- | --- |
| **이다온** | 콘솔 로그인/회원가입 백엔드DB 설계 및 디자인<br>자바 MQTT통신<br>커뮤니티 게시판<br>센서 조작기능 | [GitHub](https://github.com/DaOn1072) |
| **노건우** | 라즈베리파이 모듈 설계 및 구축<br>Python MQTT 통신구현<br>IOT구조물 설계<br>하드웨어 아키텍쳐 설계 | [GitHub](https://github.com/geonwoo1226) |
| **안창석** | 자바 MQTT통신구현<br>결과 발표<br>자바 MQTT통신<br>깃허브구축 및 설계 | [GitHub](https://github.com/AhnCSK) |


---

## 🗓️ 4. 프로젝트 일정

| 작업 항목 | 시작 날짜 | 종료 날짜 | 기간(일) |
| --- | --- | --- | --- |
| 프로젝트 정의 및 계획 수립/센서선택 | 2025-10-21 | 2025-10-21 | 1 |
| jdbc sql연동/센서입력구현 | 2025-10-21 | 2025-10-22 | 1 |
| 라즈베리파이 모듈 기능 테스트 및 클래스 작업 | 2025-10-22 | 2025-10-26 | 5 |
| 게시판완성 및 라즈베리와 통합 | 2025-10-23 | 2025-10-24 | 1 |
| 자바-라즈베리파이 MQTT 통신 | 2025-10-24 | 2025-10-28 | 5 |
| 대시보드 레이아웃 설계 | 2025-10-25 | 2025-10-26 | 1 |
| 라즈베리파이-브래드보드 모듈 구축 작업 | 2025-10-27 | 2025-10-28 | 2 |
| 인사이트 시각화 구현 | 2025-10-27 | 2025-10-27 | 1 |
| 최종 점검 및 README 작성 | 2025-10-28 | 2025-10-28 | 1 |
| 구조물 약식 구현 및 결과 발표 | 2025-10-29 | 2025-10-29 | 1 |

---

## 🏗 5. 시스템 아키텍처

1. 테이블 구조도

<img src="https://private-user-images.githubusercontent.com/70793831/509460467-e6b36bf2-a0cb-40b5-b776-17068854e675.webp?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjIyNTMzMDAsIm5iZiI6MTc2MjI1MzAwMCwicGF0aCI6Ii83MDc5MzgzMS81MDk0NjA0NjctZTZiMzZiZjItYTBjYi00MGI1LWI3NzYtMTcwNjg4NTRlNjc1LndlYnA_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTA0JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTEwNFQxMDQzMjBaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1kZmZlNzJiYzgyMjYwN2NhY2E2NzAyYzk2MTVlOGMwOGMwZWNiYTkyMGQ5OTY0MmNhYjY0MmFjZjExMmU5OWNiJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.Gp3ZEDUO1DdEpgFWOqsBgaC9MfhZUKNLFVdnyp-CP6U" width="500">


3. 사용자 콘솔 UI (Java App)

| 항목 | 설명 |
| --- | --- |
| **역할** | 사용자 인터페이스 제공 |
| **기능** | 로그인/회원가입, 센서 조회, 제어 명령, 커뮤니티, 마트 확인 등 |
| **기술** | Java (Swing 또는 JavaFX), JDBC |
| **연동** | - MySQL DB (회원 정보, 데이터 조회)<br>- MQTT 브로커 (제어 명령 송신) |

```
            │
 [1] JDBC 요청 (회원 정보, 데이터 조회)
            │
            ▼
```

3. 데이터베이스 (MySQL)

| 항목 | 설명 |
| --- | --- |
| **역할** | 시스템 전체 데이터 저장 및 조회 |
| **기능** | 회원, 센서로그, 제어기록, 게시판, 마트주문 정보 저장 |
| **기술** | MySQL |
| **연동** | - Java 앱과 JDBC로 연결<br>- Raspberry Pi에서 센서 및 제어 로그 저장 |

| 주요 테이블 | 설명 |
| --- | --- |
| `users` | 사용자 ID, 비밀번호 hash, 세대 정보 등 |
| `sensor_logs` | 온도, 움직임 등 센서 로그 |
| `controls` | 제어 이력 (LED, 문 등) |
| `posts` | 커뮤니티 게시판 글 |
| `mart_orders` | 마트 구매 내역 |

```
             │
    [2] MQTT 메시지 송/수신
             ▼
```

4. MQTT 브로커 (Mosquitto 등)

| 항목 | 설명 |
| --- | --- |
| **역할** | 센서 데이터 송신 및 제어 명령 중계 |
| **기능** | MQTT 프로토콜을 통해 메시지 송수신 |
| **기술** | MQTT (Mosquitto 등 브로커 사용) |
| **연동** | - Raspberry Pi ↔ 브로커 (센서 publish, 제어 subscribe)<br>- Java 앱 ↔ 브로커 (제어 publish) |

| 주요 토픽 예시 | 설명 |
| --- | --- |
| `/home/sensor/temperature` | 온습도 센서 값 |
| `/home/sensor/motion` | 인체감지 센서 상태 |
| `/home/control/led` | LED 제어 명령 |
| `/home/control/servo` | 서보모터 제어 명령 |
| `/home/control/buzzer` | 부저 제어 명령 |

```
             │
    [3] MQTT 메시지 처리
             ▼
```

5. Raspberry Pi (Python)

| 항목 | 설명 |
| --- | --- |
| **역할** | 센서 데이터 수집 및 제어 장치 동작 |
| **기능** | 센서 Publish, 제어 Subscribe, DB 저장 |
| **기술** | Python, paho-mqtt, RPi.GPIO, MySQL Connector |
| **연동** | - MQTT 브로커: 센서 데이터 전송 & 제어 명령 수신<br>- MySQL: 로그 저장 |

| 하드웨어 구성 | 동작 설명 |
| --- | --- |
| 온습도 센서 | 온도/습도 측정 → MQTT Publish |
| PIR 센서 | 움직임 감지 → MQTT Publish |
| LED, 서보, 부저 | MQTT 수신 시 GPIO로 제어 |

🔐 로그인 / 회원가입 플로우

- 회원가입
    - 사용자가 콘솔 앱에서 ID, 비밀번호 입력 → Java 앱에서 DB로 전송
    - 비밀번호는 해시화(예: SHA-256, BCrypt 등)하여 저장
    - 성공 시, 사용자 고유 정보(세대번호 등)도 등록
- 로그인
    - 입력된 ID/PW → DB에서 조회 및 해시 비교
    - 성공 시 세션 생성 (간단한 사용자 상태 유지 가능)
    - 회원 정보 기반 기능 연동
    - 로그인된 사용자만 제어 기능/게시판/마트 조회 가능
    - 각 사용자별 데이터 필터링: 세대별 센서 상태, 내 구매 내역 등
    

💡 데이터 흐름 요약

| 기능 | 흐름 |
| --- | --- |
| **회원가입** | Java 앱 → JDBC → DB `users` 테이블 |
| **로그인** | Java 앱 → DB 비밀번호 검증 → 성공 시 사용자 세션 유지 |
| **센서 이벤트 감지** | Raspberry Pi → MQTT Publish → Java App Subscribe → 콘솔 출력 + DB 저장 |
| **장치 제어** | Java 앱 → MQTT Publish → Raspberry Pi Subscribe → 제어 실행 |
| **데이터 저장** | Raspberry Pi / Java → MySQL (로그 저장, 게시글 저장 등) |

### 🧰 기술 스택

| 계층 | 기술 / 도구 |
| --- | --- |
| UI (사용자 콘솔) | Java 콘솔 앱 |
| 사용자 인증 | Java + JDBC + MySQL (`users` 테이블) |
| 통신 계층 | MQTT (Mosquitto 브로커) |
| IoT 제어 | 사용모델 및 언어 : Raspberry Pi + Python<br>사용 라이브러리 : GPIO, smbus2, Adafruit-DHT(DHT11), Spidev, mfrc522 |
| DB 계층 | MySQL (회원, 센서, 게시판, 구매 로그 등) |
| MQTT 라이브러리 | Eclipse Paho (Java, Python 클라이언트 모두 지원) |

📌 구조 특징 요약

✅ 로그인 기반 사용자 식별<br>✅ MQTT로 IoT 장치 실시간 제어/수신<br>✅ Java ↔ DB 연동으로 사용자 맞춤형 기능 제공<br>✅ Raspberry Pi에서 센서 상태 지속 감지 및 전송<br>✅ 보안 기능 강화 가능 (비밀번호 해시, 외출모드)


---


## ⚠️ 6. 문제 및 해결 방안

| 문제 | 원인 | 해결 방안 |
| --- | --- | --- |
| **문제1** |  |
| **문제2** |  |
| **문제3** |  |
| **문제4** |  |  |
| **문제5** |  |
| **문제6** |  |


---

## ⚠️ 7.최종결과 및 개선필요점

---

## 🧾 8. 참고문서

- https://koreascience.kr/article/JAKO201532742223692.pdf
- https://wnsgml972.github.io/mqtt/2018/03/05/mqtt/
- https://www.sci-gifted-festival.kr/data/file/pro1/3552203508_Mh2AiI5l_eed8d51609c9c514a6cfbf297243a00a988c8fd9.pdf
- https://github.com/suryamurugan/MQTT-to-MYSQL-DB---Python-
- https://koreascience.kr/article/CFKO202433162507491.view
- https://machbase.medium.com/about-mqtt-30600ec1cbe9
- https://techblog.woowahan.com/2540/
