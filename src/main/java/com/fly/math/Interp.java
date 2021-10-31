package com.fly.math;

public interface Interp {
    Interp linear = a -> a;
    Interp reverse = a -> 1f - a;
    /**
     * Aka "smoothstep".
     */
    Interp smooth = a -> a * a * (3 - 2 * a);

    //
    Interp smooth2 = a -> {
        a = a * a * (3 - 2 * a);
        return a * a * (3 - 2 * a);
    };

    Interp one = a -> 1f;

    /**
     * By Ken Perlin.
     */
    Interp smoother = a -> a * a * a * (a * (a * 6 - 15) + 10);
    Interp fade = smoother;
    Interp sine = a -> (float) ((1 - Math.cos(a * Math.PI)) / 2);
    Interp sineIn = a -> (float) (1 - Math.cos(a * Math.PI / 2));
    Interp sineOut = a -> (float) Math.sin(a * Math.PI / 2);
    Interp circle = a -> {
        if (a <= 0.5f) {
            a *= 2;
            return (1 - (float) Math.sqrt(1 - a * a)) / 2;
        }
        a--;
        a *= 2;
        return ((float) Math.sqrt(1 - a * a) + 1) / 2;
    };
    Interp circleIn = a -> 1 - (float) Math.sqrt(1 - a * a);
    Interp circleOut = a -> {
        a--;
        return (float) Math.sqrt(1 - a * a);
    };

    /**
     * @param a Alpha value between 0 and 1.
     */
    float apply(float a);

    /**
     * @param a Alpha value between 0 and 1.
     */
    default float apply(float start, float end, float a) {
        return start + (end - start) * apply(a);
    }
}
