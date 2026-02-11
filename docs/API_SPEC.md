# 두구두구 백엔드 API 명세

프론트엔드 하드코딩 데이터를 기준으로 한 REST API 명세입니다.  
프론트는 `VITE_API_BASE_URL`로 이 백엔드 base URL을 지정합니다.

---

## 1. 공통 사항

- **Content-Type**: 요청/응답 모두 `application/json`
- **인코딩**: UTF-8
- **에러 응답**: HTTP 4xx/5xx + JSON body (선택)

```json
{
  "error": "ERROR_CODE",
  "message": "사용자에게 보여줄 메시지"
}
```

---

## 2. 필터 설정 (학과·학년·키워드)

**용도**: `/events` 페이지의 캘린더 필터 옵션(학과 선택, 학년, 키워드 그룹) 제공.

### `GET /api/filter-config`

**응답**: `200 OK`

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `departmentsByCollege` | array | O | 대학별 학과 목록 |
| `departmentsByCollege[].college` | string | O | 대학명 |
| `departmentsByCollege[].departments` | string[] | O | 해당 대학 소속 학과명 배열 |
| `years` | string[] | O | 학년 옵션 목록 |
| `keywordGroups` | array | O | 키워드 그룹(라벨 + 키워드 배열) |
| `keywordGroups[].label` | string | O | 그룹 표시명 (예: 학사공지, 두드림) |
| `keywordGroups[].keywords` | string[] | O | 해당 그룹 키워드 목록 |

### 응답 예시 (프론트 기본값 기준)

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
    },
    {
      "college": "호텔관광대학",
      "departments": [
        "호텔관광외식경영학부 호텔관광경영학전공",
        "호텔관광외식경영학부 외식경영학전공",
        "호텔외식관광프랜차이즈경영학과",
        "조리서비스경영학과"
      ]
    },
    {
      "college": "자연과학대학",
      "departments": ["수학통계학과", "물리천문학과", "화학과"]
    },
    {
      "college": "생명과학대학",
      "departments": [
        "생명시스템학부 식품생명공학전공",
        "생명시스템학부 바이오융합공학전공",
        "생명시스템학부 바이오산업자원공학전공",
        "스마트생명산업융합학과"
      ]
    },
    {
      "college": "인공지능융합대학",
      "departments": [
        "AI융합전자공학과",
        "반도체시스템공학과",
        "컴퓨터공학과",
        "정보보호학과",
        "양자지능정보학과",
        "창의소프트학부 디자인이노베이션전공",
        "창의소프트학부 만화애니메이션텍전공",
        "사이버국방학과",
        "국방AI로봇융합공학과",
        "인공지능데이터사이언스학과",
        "AI로봇학과",
        "지능정보융합학과",
        "콘텐츠소프트웨어학과"
      ]
    },
    {
      "college": "공과대학",
      "departments": [
        "건축공학과",
        "건축학과",
        "건설환경공학과",
        "환경융합공학과",
        "에너지자원공학과",
        "기계공학과",
        "우주항공시스템공학부 우주항공공학전공",
        "우주항공시스템공학부 항공시스템공학전공",
        "우주항공시스템공학부 지능형드론융합전공",
        "나노신소재공학과",
        "양자원자력공학과",
        "국방AI융합시스템공학과"
      ]
    },
    {
      "college": "예체능대학",
      "departments": [
        "회화과",
        "패션디자인학과",
        "음악과",
        "체육학과",
        "무용과",
        "영화예술학과"
      ]
    },
    {
      "college": "대양휴머니티칼리지",
      "departments": ["자유전공학부"]
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

## 3. 이벤트 브라우저 카테고리

**용도**: `/events` 페이지 상단 필터 pill(All, Academic Calendar 등) 목록.

### `GET /api/event-categories`

**응답**: `200 OK`

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `categories` | string[] | O | 표시할 카테고리 라벨 목록. 첫 번째는 보통 `"All"`. |

### 응답 예시

```json
{
  "categories": [
    "All",
    "Academic Calendar",
    "Student Council Events",
    "Club Recruitment",
    "Department Seminars",
    "External Competitions"
  ]
}
```

---

## 4. 이벤트(공지) 목록

**용도**: 캘린더 필터/이벤트 브라우저에 표시할 공지·이벤트 목록.  
프론트에는 **학사 일정**과 **두드림** 두 종류 소스가 있으며, 백엔드에서 통합해 내려줄 수 있습니다.

### `GET /api/events`

**Query (선택)**

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| `department` | string | 학과 필터 (예: `컴퓨터공학과`, `전체`) |
| `years` | string | 복수 시 콤마 구분 (예: `1st Year,2nd Year`) |
| `keywords` | string | 복수 시 콤마 구분 (예: `수강신청,학사일정`) |
| `from` | string | ISO 8601 날짜 (이후 이벤트만) |
| `to` | string | ISO 8601 날짜 (이전 이벤트만) |

**응답**: `200 OK` — 이벤트 객체 배열

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `id` | string | O | 고유 식별자 |
| `title` | string | O | 제목 (표시용) |
| `category` | string | O | 프론트 표시용 카테고리: `Academic`, `Career`, `Social`, `Competition`, `Workshop` 중 하나 |
| `originalCategory` | string | X | 원본 한글 카테고리 (예: `성적/시험`, `예체능/워크샵`) |
| `date` | string | O | 날짜만 `YYYY-MM-DD` |
| `start` | string | X | 시작 시각 ISO 8601 (예: `2026-02-10T00:00:00Z`). 없으면 프론트에서 `daysLeft` 0 처리 |
| `end` | string | X | 종료 시각 ISO 8601 |
| `location` | string | O | 장소 |
| `department` | string | O | 대상 학과/부서. `"전체"`면 프론트에서 `All Departments`로 표시 가능 |
| `attendees` | number | X | 참가/정원 수. 70 이상이면 프론트에서 인기 배지 표시 (기본값 0) |
| `description` | string | O | 본문/설명 |
| `image` | string | X | 이미지 URL |

### 카테고리 매핑 (참고)

프론트 한글 카테고리 → `category` 값:

| originalCategory (한글) | category (영문) |
|-------------------------|-----------------|
| 성적/시험, 등록/휴복학, 수강신청, 학사일정, 학술/포럼 | Academic |
| 공휴일, 봉사/인공지능 | Social |
| 예체능/워크샵, 학습/IT | Workshop |
| (기타) | Academic |

### 응답 예시 (학사 일정 + 두드림 혼합)

```json
[
  {
    "id": "2026-02-01",
    "title": "1학기 수강신청 (4학년)",
    "category": "Academic",
    "originalCategory": "수강신청",
    "date": "2026-02-10",
    "start": "2026-02-10T00:00:00Z",
    "end": "2026-02-10T23:59:59Z",
    "location": "Sejong University",
    "department": "전체",
    "attendees": 67,
    "description": "1학기 수강신청 (4학년)"
  },
  {
    "id": "dd-2026-001-apply",
    "title": "[신청] 동계 영화제작워크샵 : 초록제",
    "category": "Workshop",
    "originalCategory": "예체능/워크샵",
    "date": "2026-02-05",
    "start": "2026-02-05T00:00:00Z",
    "end": "2026-02-12T23:59:59Z",
    "location": "영화예술학과",
    "department": "영화예술학과",
    "attendees": 100,
    "description": "대상: 영화예술학과 / 신청 인원: 무제한"
  }
]
```

---

## 5. 엔드포인트 요약

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/filter-config` | 필터 설정(학과·학년·키워드 그룹) |
| GET | `/api/event-categories` | 이벤트 브라우저 카테고리 목록 |
| GET | `/api/events` | 이벤트(공지) 목록 (쿼리 필터 선택) |

---

## 6. (참고) 기존 ICS 피드

테스트 코드 상에는 아래 API가 가정되어 있습니다. 본 명세와 별도로 유지 가능합니다.

- **GET** `/ics/feed?token={token}&download={boolean}`
  - `token`: 유효한 토큰
  - `download=true` 시 `Content-Disposition: attachment; filename="schedule.ics"` 기대
  - 잘못된 token 형식 → 400, 존재하지 않는 token → 404

---

*문서 버전: 1.0 (프론트 하드코딩 데이터 기준)*
