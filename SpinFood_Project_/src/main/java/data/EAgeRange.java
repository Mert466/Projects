package data;

public class EAgeRange {

    /**
     * The AgeRange enum represents the different age ranges.
     */
    public enum AgeRange {
        RANGE_0_17(0),
        RANGE_18_23(1),
        RANGE_24_27(2),
        RANGE_28_30(3),
        RANGE_31_35(4),
        RANGE_36_41(5),
        RANGE_42_46(6),
        RANGE_47_56(7),
        RANGE_57_OLDER(8);

        private final int value;

        /**
         * Constructs an AgeRange enum constant with the specified value.
         *
         * @param value the value associated with the age range
         */
        AgeRange(int value) {
            this.value = value;
        }

        /**
         * Retrieves the value associated with the age range.
         *
         * @return the value associated with the age range
         */
        public int getValue() {
            return value;
        }
    }

}