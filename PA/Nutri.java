class Nutri {
    private final ImList<Pair<String, Integer>> nutriLst;

    public Nutri() {
        this.nutriLst = new ImList<>();
    }

    public Nutri(String name, int amt) {
        ImList<Pair<String, Integer>> temp = new ImList<>();
        this.nutriLst = temp.add(new Pair<>(name, amt));
    }

    private Nutri(Nutri nutri, String name, int amt) {
        for (int i=0; i < nutri.nutriLst.size(); i++) {
            Pair<String, Integer> pair = nutri.nutriLst.get(i);
            if (pair.first().equals(name)) {
                this.nutriLst = nutri.nutriLst.set(
                    i, new Pair<>(pair.first(), pair.second() + amt));
                return;
            }
        }

        this.nutriLst = nutri.nutriLst.add(new Pair<>(name, amt))
        .sort(new PairFirstComparator<>());
    }

    private Nutri(Nutri n1, Nutri n2) {
        ImList<Pair<String, Integer>> nutriLst_temp = n1.nutriLst;

        for (Pair<String, Integer> pair2 : n2.nutriLst) {
            boolean s = false;
            for (int i=0; i < n1.nutriLst.size(); i++) {
                Pair<String, Integer> pair1 = n1.nutriLst.get(i);
                if (pair1.first().equals(pair2.first())) {
                    nutriLst_temp = nutriLst_temp.set(
                        i, new Pair<>(pair1.first(), pair1.second() + pair2.second()));
                    s = true;
                    break;
                }
            }
            if (!s) {
                nutriLst_temp = nutriLst_temp.add(new Pair<>(pair2.first(), pair2.second()));
            }
        }

        this.nutriLst = nutriLst_temp.sort(new PairFirstComparator<>());
    }

    public Nutri update(String nutrient, int amt) {
        return new Nutri(this, nutrient, amt);
    }

    public Nutri update(Nutri nutri) {
        return new Nutri(this, nutri);
    }

    public String grade() {
        int fat = 0;
        int sugar = 0;
        for (Pair<String, Integer> p : this.nutriLst) {
            if (p.first().equals("fat")) {
                fat = p.second();
            }
            else if (p.first().equals("sugar")) {
                sugar = p.second();
            }
        }
        if (fat <= 1680 && sugar <= 2400) {
            return "A";
        }
        if (fat <= 2880 && sugar <= 12000) {
            return "B";
        }
        if (fat <= 6720 && sugar <= 24000) {
            return "C";
        }
        return "D";
    }

    @Override
    public String toString() { 
        return nutriLst.toString();
    }
}
