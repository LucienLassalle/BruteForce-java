public class testUnitaire {
    public static void main(String[] args) throws Exception {
        String test = decryptSHA256.hashSHA256("Y*opx04>O");
        String test2 = decryptSHA256.hashSHA256("CLemeilleur");
        int resultatCT = test.compareTo(test2);
        System.out.println("test: " + test);
        System.out.println("test2: " + test2);
        System.out.println("resultatCT: " + resultatCT);
    }
}
