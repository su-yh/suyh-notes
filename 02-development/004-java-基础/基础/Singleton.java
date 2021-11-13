

public Singleton {
    private Singleton() {}

    // 延迟加载
    static class Lazy() {
        public static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return Lazy.instance;
    }
}

