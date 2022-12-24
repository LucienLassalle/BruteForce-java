import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Objects;

public class decryptSHA256 {
    public static boolean speciale;

    public static void speciale(boolean valeur){
        speciale= valeur;
    }
    /**
     * Hashage en SHA-256
     * @param text input d'un mot
     * @return Output du hashage du mot
     * @throws Exception En cas d'erreur
     */
    public static String hashSHA256(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte[] byteData = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte byteDatum : byteData) {
            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


    /**
     * Obtenir le mot suivant, avec un découpage grâce à un ArrayList
     * @param word input du mot
     * @return mot
     */
    public static String nextWord(String word) {
        ArrayList<String> motDecoupe =mot(word);
        return algorithmNext(motDecoupe);
    }
    public static ArrayList<String> mot(String word){
        ArrayList<String> mot = new ArrayList<>();
        int i = 0;
        while(word.length()>i){
            mot.add(String.valueOf(word.charAt(i)));
            i++;
        }
        return mot;
    }

    /**
     * Algorithme de préparation d'une nouvelle chaîne de caractère. NOTE : Modifier le code afin que "Z" ne soit plus la fin de la chaine de caractère
     * @param mot ArrayList contenant des caractères transformés en chaine de caractère issu de l'ancien mot.
     * @return Le nouveau mot à tester.
     */
    public static String algorithmNext(ArrayList<String> mot) {
        char[] characters;
        if(speciale){
            characters = "1234567890°+&é(-è_çà)=^$*ù!:;,?./§abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); // ' et " ne sont pas traité.
        } else {
            characters = "1234567890abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        }
        StringBuilder newString= new StringBuilder();

        if(contientUniquementLast(mot)){
            int i = 0;
            newString.insert(0, "1");
            while(mot.size()>i){
                newString.insert(0, '1');
                i++;
            }
            return newString.toString();
        }
        else if (mot.get(mot.size()-1).equals("Z")) {
            int i = 1;
            while(mot.get(mot.size()-i).equals("Z")) {
                mot.set(mot.size()-i, "1");
                i++;
            }
            String last;
            int j = 0;
            while(!Objects.equals(mot.get(mot.size() - i), String.valueOf(characters[j]))){
                if(mot.get(mot.size()-i).equals(String.valueOf(characters[j]))){
                    break;
                }
                j++;
            }
            last = String.valueOf(characters[j+1]);
            mot.set(mot.size()-i, last);
            int k=0;
            while(mot.size()>k){
                newString.append(mot.get(k));
                k++;
            }
        }
        else {
            int j = 0;
            while (!(mot.get(mot.size()-1).equals(String.valueOf(characters[j])))) {
                j++;
            }
            mot.set(mot.size()-1, String.valueOf(characters[j+1]));
            int k=0;
            while(mot.size()>k){
                newString.append(mot.get(k));
                k++;
            }
        }
        return newString.toString();
    }


    /**
     * Vérifie que le mot ne contient que des "Z"
     * @param mot Arraylist contenant les lettres du mot
     * @return false si une lettre n'est pas "Z"
     */
    public static boolean contientUniquementLast(ArrayList<String> mot){
        int i=0;
        while(mot.size()>i){
            if(mot.get(i).equals("Z")){
                i++;
            } else {
                return false;
            }
        }
        return true;
    }
}
