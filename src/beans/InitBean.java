package beans;

import java.io.Serializable;
import java.sql.*;
/**
 *
 * @author Dave
 */
public class InitBean implements Serializable{

    public String doInit() {
        Connect ct = new Connect();
        ConnectionObject co = ct.getConnection();
        if(co.getMessage().equals("OK")) {
            Connection con = co.getConnection();
            try {
                PreparedStatement student = con.prepareStatement(createStudent());
                student.executeUpdate();
                student.clearBatch();
                student.executeUpdate(createGarant());
                student.executeUpdate(createPredmet());
                student.executeUpdate(createRozvrh());
                student.executeUpdate(createPrubeh());
                student.close();
                ct.closeConnection(con);
            } catch(SQLException e) {
                co.setMessage("INFO");
                //co.setMessage(e.getMessage());
            }
        }
        return co.getMessage();
    }

    public String createStudent() {
        return "CREATE TABLE Student (idStudent INTEGER PRIMARY KEY, jmeno VARCHAR(30) NOT NULL, " +
                "heslo VARCHAR(30) NOT NULL)";
    }

    public String createGarant() {
        return "CREATE TABLE Garant (idGarant INTEGER PRIMARY KEY, jmeno VARCHAR(30) NOT NULL, " +
                "heslo VARCHAR(30) NOT NULL)";
    }

    public String createPredmet() {
        return "CREATE TABLE Predmet (idPredmet INTEGER PRIMARY KEY, " +
                "nazev VARCHAR(30) NOT NULL, kredity INTEGER, garant INTEGER, " +
                "FOREIGN KEY (garant) REFERENCES Garant(idGarant) " +
                "ON UPDATE CASCADE ON DELETE CASCADE)";
    }

    public String createRozvrh() {
        return "CREATE TABLE Rozvrh (idRozvrh INTEGER PRIMARY KEY, " +
                "idPredmet INTEGER, " +
                "FOREIGN KEY (idPredmet) REFERENCES Predmet(idPredmet) " +
                "ON UPDATE CASCADE ON DELETE CASCADE, " +
                "den ENUM('Po', 'Út', 'St', 'Čt', 'Pá'), " +
                "od TIME, do TIME)";
    }

    public String createPrubeh() {
        return "CREATE TABLE Prubeh (idStudent INTEGER, " +
                "idPredmet INTEGER, idRozvrh INTEGER, body INTEGER, " +
                "FOREIGN KEY (idStudent) REFERENCES Student(idStudent) " +
                "ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (idPredmet) REFERENCES Predmet(idPredmet) " +
                "ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (idRozvrh) REFERENCES Rozvrh(idRozvrh) " +
                "ON UPDATE CASCADE ON DELETE CASCADE, " +
                "zapis DATE NOT NULL, CONSTRAINT PK_Prubeh PRIMARY KEY (idPredmet, idStudent))";
    }

}
