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
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hash?useSSL=false", "root", "");
        Statement stmt = con.createStatement();
        demandeUtilisateur();
        long debut = System.currentTimeMillis();
        boolean motDePasseTrouve = false;
        String test = decryptSHA256.hashSHA256("ZZZZZZZZZZZZZZZZ");
        System.out.println("test: " + test);

        String hash = "";
        ResultSet rs = stmt.executeQuery("SELECT motdepasse FROM sha256 WHERE hash=\"0\";");
        String lastRS = "1";
        while (rs.next()) {
            lastRS = rs.getString("motdepasse");
        }
        String TentativeMDP = lastRS;
        while (!motDePasseTrouve) {
            if (decryptSHA256.hashSHA256(TentativeMDP).equals(test)) {
                System.out.println("Mot de passe trouvé: " + TentativeMDP);
                motDePasseTrouve = true;
            } else {
                TentativeMDP = decryptSHA256.nextWord(TentativeMDP);
                hash = decryptSHA256.hashSHA256(TentativeMDP);
                //System.out.println(TentativeMDP + " - " + hash);
                basededonnee.insertSQL(TentativeMDP, hash);
            }
            try {
                String insert = "INSERT INTO sha256 (motdepasse, hash) VALUES ('" + TentativeMDP + "', '" + hash + "')";
                stmt.executeUpdate(insert);
            } catch (Exception e) {

            }
            if(TentativeMDP.contains("ZZ")){
                try {
                    String insertReplace = "UPDATE sha256 SET motdepasse = '"+TentativeMDP+"' WHERE hash=\"0\";";
                    stmt.executeUpdate(insertReplace);
                    System.out.println("Insertion : "+TentativeMDP);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur grave de sauvegarde");
                    System.exit(0);
                }
            }
        }
        long fin = System.currentTimeMillis();
        fin = fin - debut;
        System.out.println("[Fin d'exécution : " + fin + "ms]");
    }
}
// TODO : Avoir la possibilité de faire une attaque par dictionnaire grâce a un SQL
// TODO : Sauvegarder l'ensemble des tests dans un fichier SQL --> OK
// TODO : Reprendre a partir du dernier test issu du fichier SQL
// TODO : Tester uniquement avec la BDD
// TODO : Avoir la possibilité de tester uniquement un hash (sans conversion)
// TODO : Avoir la possibilité de limiter le nombre de caractère (mot.size())
