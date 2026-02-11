package com.doogoo.doogoo.event.domain;

import java.util.Set;

public final class DoDreamConstants {

    public static final Set<String> DO_DREAM_CATEGORIES = Set.of(
            "예체능/워크샵",
            "봉사/인공지능",
            "학술/포럼",
            "학습/IT"
    );

    public static boolean isDoDream(String originalCategory) {
        return originalCategory != null && DO_DREAM_CATEGORIES.contains(originalCategory);
    }

    private DoDreamConstants() {
    }
}
