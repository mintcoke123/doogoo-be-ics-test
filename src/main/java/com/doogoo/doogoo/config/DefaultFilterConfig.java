package com.doogoo.doogoo.config;

import com.doogoo.doogoo.dto.CollegeDepartmentDto;
import com.doogoo.doogoo.dto.FilterConfigResponse;
import com.doogoo.doogoo.dto.KeywordGroupDto;
import java.util.List;

public final class DefaultFilterConfig {

    public static final FilterConfigResponse VALUE = new FilterConfigResponse(
            getDepartmentsByCollege(),
            List.of("1st Year", "2nd Year", "3rd Year", "4th Year"),
            List.of(
                    new KeywordGroupDto("학사공지", List.of("수강신청", "성적/시험", "등록/휴복학", "학사일정", "공휴일")),
                    new KeywordGroupDto("두드림", List.of("예체능/워크샵", "봉사/인공지능", "학술/포럼", "학습/IT"))
            )
    );

    private static List<CollegeDepartmentDto> getDepartmentsByCollege() {
        return List.of(
                new CollegeDepartmentDto("인문과학대학", List.of(
                        "국어국문학과", "국제학부 영어데이터융합전공", "국제학부 국제일본학전공", "국제학부 중국통상학전공",
                        "역사학과", "교육학과", "글로벌인재학부 한국언어문화전공", "글로벌인재학부 국제통상전공", "글로벌인재학부 국제협력전공")),
                new CollegeDepartmentDto("사회과학대학", List.of("행정학과", "미디어커뮤니케이션학과", "법학과")),
                new CollegeDepartmentDto("경영경제대학", List.of("경영학부", "경제학과")),
                new CollegeDepartmentDto("호텔관광대학", List.of(
                        "호텔관광외식경영학부 호텔관광경영학전공", "호텔관광외식경영학부 외식경영학전공",
                        "호텔외식관광프랜차이즈경영학과", "조리서비스경영학과")),
                new CollegeDepartmentDto("자연과학대학", List.of("수학통계학과", "물리천문학과", "화학과")),
                new CollegeDepartmentDto("생명과학대학", List.of(
                        "생명시스템학부 식품생명공학전공", "생명시스템학부 바이오융합공학전공",
                        "생명시스템학부 바이오산업자원공학전공", "스마트생명산업융합학과")),
                new CollegeDepartmentDto("인공지능융합대학", List.of(
                        "AI융합전자공학과", "반도체시스템공학과", "컴퓨터공학과", "정보보호학과", "양자지능정보학과",
                        "창의소프트학부 디자인이노베이션전공", "창의소프트학부 만화애니메이션텍전공", "사이버국방학과",
                        "국방AI로봇융합공학과", "인공지능데이터사이언스학과", "AI로봇학과", "지능정보융합학과", "콘텐츠소프트웨어학과")),
                new CollegeDepartmentDto("공과대학", List.of(
                        "건축공학과", "건축학과", "건설환경공학과", "환경융합공학과", "에너지자원공학과", "기계공학과",
                        "우주항공시스템공학부 우주항공공학전공", "우주항공시스템공학부 항공시스템공학전공", "우주항공시스템공학부 지능형드론융합전공",
                        "나노신소재공학과", "양자원자력공학과", "국방AI융합시스템공학과")),
                new CollegeDepartmentDto("예체능대학", List.of("회화과", "패션디자인학과", "음악과", "체육학과", "무용과", "영화예술학과")),
                new CollegeDepartmentDto("대양휴머니티칼리지", List.of("자유전공학부"))
        );
    }

    private DefaultFilterConfig() {
    }
}
