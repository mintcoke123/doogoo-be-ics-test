# 두구두구 백엔드 API 명세서

기준: 현재 백엔드 소스(`api`, `cal`, `dto`, `config`) 반영.

---

## 1. GET /api/departments

학과 목록(필터용 단순 목록).

**요청**
- Method: `GET`
- Path: `/api/departments`
- Query: 없음

**응답** `200 OK`  
Content-Type: `application/json`

| 필드 | 타입 | 설명 |
|------|------|------|
| departments | array | 학과 항목 목록 |
| departments[].id | string | 학과 ID |
| departments[].name | string | 학과명 |

**예상 답변 예시**
```json
{
  "departments": [
    { "id": "1", "name": "컴퓨터공학과" },
    { "id": "2", "name": "소프트웨어학과" },
    { "id": "3", "name": "전자정보공학과" },
    { "id": "0", "name": "전체" }
  ]
}
```

---

## 2. GET /api/keywords

키워드 목록(필터용).

**요청**
- Method: `GET`
- Path: `/api/keywords`
- Query: 없음

**응답** `200 OK`  
Content-Type: `application/json`

| 필드 | 타입 | 설명 |
|------|------|------|
| keywords | array | 키워드 항목 목록 |
| keywords[].id | string | 키워드 ID |
| keywords[].name | string | 키워드명 |

**예상 답변 예시**
```json
{
  "keywords": [
    { "id": "1", "name": "해커톤" },
    { "id": "2", "name": "수강신청" },
    { "id": "3", "name": "학사일정" }
  ]
}
```

---

## 3. GET /api/notices

두드림 공지 목록. 시드 데이터 기준으로 `originalCategory`가 두드림인 이벤트만, `startTime` 기준 정렬.

**요청**
- Method: `GET`
- Path: `/api/notices`
- Query: 없음

**응답** `200 OK`  
Content-Type: `application/json`

| 필드 | 타입 | 설명 |
|------|------|------|
| notices | array | 공지 항목 목록 |
| notices[].noticeId | string | 공지 ID (externalId) |
| notices[].title | string | 제목 |
| notices[].department | string | 학과/소속 |
| notices[].applicationDate | string | 신청 시작일 (yyyy-MM-dd, Asia/Seoul) |
| notices[].operatingDate | string | 운영일/종료일 (yyyy-MM-dd, Asia/Seoul) |

**예상 답변 예시** (시드 데이터 기준)
```json
{
  "notices": [
    {
      "noticeId": "dd-2026-001-apply",
      "title": "[신청] 동계 영화제작워크샵 : 초록제",
      "department": "영화예술학과",
      "applicationDate": "2026-02-05",
      "operatingDate": "2026-02-12"
    },
    {
      "noticeId": "dd-2026-001-run",
      "title": "[운영] 동계 영화제작워크샵 : 초록제",
      "department": "영화예술학과",
      "applicationDate": "2026-02-13",
      "operatingDate": "2026-02-13"
    },
    {
      "noticeId": "dd-2026-002-apply",
      "title": "[신청] 인융인의 재능봉사",
      "department": "인공지능융합대학",
      "applicationDate": "2026-02-09",
      "operatingDate": "2026-02-25"
    },
    {
      "noticeId": "dd-2026-003-apply",
      "title": "[신청] 제9회 학정포럼",
      "department": "학술정보원",
      "applicationDate": "2026-02-19",
      "operatingDate": "2026-03-05"
    },
    {
      "noticeId": "dd-2026-004-run",
      "title": "[운영] 기초학력증진프로그램(1차) : 파이썬",
      "department": "교수학습개발센터",
      "applicationDate": "2026-04-01",
      "operatingDate": "2026-06-28"
    }
  ]
}
```

---

## 4. GET /api/filter-config

필터 설정(단과대별 학과, 학년, 키워드 그룹).

**요청**
- Method: `GET`
- Path: `/api/filter-config`
- Query: 없음

**응답** `200 OK`  
Content-Type: `application/json`

| 필드 | 타입 | 설명 |
|------|------|------|
| departmentsByCollege | array | 단과대별 학과 목록 |
| departmentsByCollege[].college | string | 단과대명 |
| departmentsByCollege[].departments | string[] | 학과명 목록 |
| years | string[] | 학년 옵션 (예: "1st Year" 등) |
| keywordGroups | array | 키워드 그룹 목록 |
| keywordGroups[].label | string | 그룹 라벨 |
| keywordGroups[].keywords | string[] | 키워드 문자열 목록 |

**예상 답변 예시** (일부 생략)
```json
{
  "departmentsByCollege": [
    {
      "college": "인문과학대학",
      "departments": [
        "국어국문학과",
        "국제학부 영어데이터융합전공",
        "국제학부 국제일본학전공",
        "국제학부 중국통상학전공",
        "역사학과",
        "교육학과",
        "글로벌인재학부 한국언어문화전공",
        "글로벌인재학부 국제통상전공",
        "글로벌인재학부 국제협력전공"
      ]
    },
    {
      "college": "사회과학대학",
      "departments": ["행정학과", "미디어커뮤니케이션학과", "법학과"]
    },
    {
      "college": "경영경제대학",
      "departments": ["경영학부", "경제학과"]
    }
  ],
  "years": ["1st Year", "2nd Year", "3rd Year", "4th Year"],
  "keywordGroups": [
    {
      "label": "학사공지",
      "keywords": ["수강신청", "성적/시험", "등록/휴복학", "학사일정", "공휴일"]
    },
    {
      "label": "두드림",
      "keywords": ["예체능/워크샵", "봉사/인공지능", "학술/포럼", "학습/IT"]
    }
  ]
}
```

---

## 5. POST /api/ics

선택한 공지로 ICS 구독을 생성하고, 캘린더 URL을 반환한다.

**요청**
- Method: `POST`
- Path: `/api/ics`
- Content-Type: `application/json`

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| selectedNoticed | string[] | N | 선택한 공지 ID 목록 (`noticeId` 값). null/빈 배열 가능 |
| alarmEnabled | boolean | N | 알람 사용 여부. 기본 false |
| alarmMinutesBefore | integer | N | 알람 분 단위(0~10080). 범위 밖이면 무시 |

**응답** `201 Created`  
Content-Type: `application/json`

| 필드 | 타입 | 설명 |
|------|------|------|
| token | string | 구독 토큰 (6자 영숫자) |
| icsUrl | string | ICS 피드 URL (브라우저에서 구독용) |
| downloadUrl | string | 다운로드용 URL (`?download=true`) |

**요청 예시**
```json
{
  "selectedNoticed": ["dd-2026-001-apply", "dd-2026-001-run", "dd-2026-003-apply"],
  "alarmEnabled": true,
  "alarmMinutesBefore": 60
}
```

**예상 답변 예시**
```json
{
  "token": "Ab3xYz",
  "icsUrl": "https://example.railway.app/cal/Ab3xYz.ics",
  "downloadUrl": "https://example.railway.app/cal/Ab3xYz.ics?download=true"
}
```
(실제 `token`은 매 요청마다 새로 생성되며, `icsUrl`/`downloadUrl`의 호스트는 `app.base-url` 설정에 따름.)

---

## 6. GET /cal/{token}.ics

해당 토큰의 ICS 피드를 반환한다.

**요청**
- Method: `GET`
- Path: `/cal/{token}.ics`
- Query:
  - `download` (optional): `true` 시 `Content-Disposition: attachment; filename="schedule.ics"` 로 내려줌. 기본 `false`

**응답** `200 OK`  
- Content-Type: `text/calendar; charset=UTF-8`  
- Body: iCalendar 형식 문자열 (UTF-8)

**예상 답변**  
- 본문은 iCalendar 형식. `selectedNoticed`에 해당하는 이벤트가 포함되며, `alarmEnabled`가 true면 VALARM 블록 포함.
- 토큰이 없으면 `404` (Subscription not found).

**예시 (일부)**
```
BEGIN:VCALENDAR
VERSION:2.0
PRODID:-//Doogoo//ICS//EN
...
BEGIN:VEVENT
UID:dd-2026-001-apply@doogoo
DTSTAMP:...
DTSTART:...
DTEND:...
SUMMARY:[신청] 동계 영화제작워크샵 : 초록제
...
BEGIN:VALARM
TRIGGER:-PT60M
ACTION:DISPLAY
...
END:VALARM
END:VEVENT
...
END:VCALENDAR
```

---

## 에러 응답

| 상황 | HTTP | 설명 |
|------|------|------|
| 구독 없음 (GET /cal/{token}.ics) | 404 | 해당 token 구독이 없을 때 |
| 잘못된 요청 (POST /api/ics 등) | 400 | BadRequestException 등 |

---

## 참고

- **Base URL**: 배포 환경에서는 `app.base-url`(또는 `APP_BASE_URL`)에 따라 달라짐. 로컬 기본값 `http://localhost:8080`.
- **공지 ID**: `/api/notices` 의 `noticeId` 값을 그대로 `POST /api/ics` 의 `selectedNoticed` 배열에 사용.
- **날짜**: `/api/notices` 의 날짜는 Asia/Seoul 기준 `yyyy-MM-dd` 문자열.
