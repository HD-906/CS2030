class Audit {
    private static final ImList<String> GRADE_LIST = new ImList<String>()
    .add("A").add("B").add("C").add("D");
    private final ImList<Pair<String, Integer>> result;

    Audit() {
        ImList<Pair<String, Integer>> tempList = new ImList<>();
        for (String grade : GRADE_LIST) {
            tempList = tempList.add(new Pair<>(grade, 0));
        }
        this.result = tempList;
    }

    private Audit(ImList<Pair<String, Integer>> pastResult, String grade) {
        ImList<Pair<String, Integer>> tempList = new ImList<>();
        for (Pair<String, Integer> p : pastResult) {
            if (p.first().equals(grade)) {
                tempList = tempList.add(new Pair<>(p.first(), p.second() + 1));
            } else {
                tempList = tempList.add(p);
            }
        }
        this.result = tempList;
    }

    public Audit add(Drink drink) {
        return new Audit(this.result, drink.value().grade());
    }

    @Override
    public String toString () {
        return this.result.toString();
    }
}
