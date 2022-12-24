import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class main_bruteforce {
    public static void demandeUtilisateur(){
        Scanner lecteur = new Scanner(System.in);
        int Dictionnaire=0; // TODO : Ajout du mode de hashage par dictionnaire
        int Speciaux = -1;
        while((Dictionnaire==-1)||(Speciaux==-1)) {
            System.out.println("Veuillez répondre au champs suivant : ");
            System.out.println("Entrez 1 si vous souhaitez utilisez le décryptage par dictionnaire. \n" +
                    "Entrez 0 si vous souhaitez utiliser le décryptage par défaut en bruteforce");
            Dictionnaire=lecteur.nextInt();
            lecteur.nextLine();
            if(Dictionnaire == 0) {
                System.out.println("Entrez 1 si vous souhaitez activer les caractères spéciaux. \n" +
                        "Entrez 0 si vous souhaitez désactiver les caractères spéciaux");
                Speciaux = lecteur.nextInt();
                lecteur.nextLine();
            } else {
                Speciaux=2;
            }
        }
        if(Dictionnaire==1){
            // TODO : Activer le mode par dictionnaire.
        } else {
            if(Speciaux == 1){
                decryptSHA256.speciale(true);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        demandeUtilisateur();
        long debut = System.currentTimeMillis();
        boolean motDePasseTrouve = false;
        String test = decryptSHA256.hashSHA256("ZZZZZZZZZZZZZZZZ");
        System.out.println("test: " + test);

        String hash = "";
        String lastRS = "1";
        String TentativeMDP = lastRS;
        while (!motDePasseTrouve) {
            if (decryptSHA256.hashSHA256(TentativeMDP).equals(test)) {
                System.out.println("Mot de passe trouvé: " + TentativeMDP);
                motDePasseTrouve = true;
            } else {
                TentativeMDP = decryptSHA256.nextWord(TentativeMDP);
                hash = decryptSHA256.hashSHA256(TentativeMDP);
                // System.out.println(TentativeMDP + " - " + hash);
            }
        }
        long fin = System.currentTimeMillis();
        fin = fin - debut;
        System.out.println("[Fin d'exécution : " + fin + "ms]");
    }
}
// TODO : Avoir la possibilité de tester uniquement un hash (sans conversion)
// TODO : Avoir la possibilité de limiter le nombre de caractère (mot.size())
