import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;

class AddMoneyConverterForm extends JFrame {
    private final JLabel titleLabel;
    private final JButton convertButton;
    private final JButton cancelButton;
    private final JLabel lblId;
    private final JLabel lblName;
    private final JLabel lblCount;
    private final JLabel lblAddress;
    private final JTextField txtId;
    JComboBox cb1;
    JComboBox cb2;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtSalary;
    private JLabel lblSalary;

    AddMoneyConverterForm() {
        setSize(400, 400);
        setTitle("Money Converter ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Money Converter ");
        titleLabel.setFont(new Font("", 1, 35));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                titleLabel.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add("North", titleLabel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        convertButton = new JButton("Convert");
        convertButton.setFont(new Font("", 1, 20));
        cancelButton = new JButton("Reset");
        cancelButton.setFont(new Font("", 1, 20));
        buttonPanel.add(convertButton);
        buttonPanel.add(cancelButton);
        add("South", buttonPanel);

//Adding center panel;
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 2));

//=======Amount=========
        lblId = new JLabel("Amount");
        lblId.setFont(new Font("", 1, 20));
        lblId.setBorder(BorderFactory.createCompoundBorder(
                lblId.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblId);
        txtId = new JTextField(20);
        txtId.setFont(new Font("", 1, 20));
        txtId.setBorder(BorderFactory.createCompoundBorder(
                txtId.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(txtId);

//=======from=========
        lblName = new JLabel("From");
        lblName.setFont(new Font("", 1, 20));
        lblName.setBorder(BorderFactory.createCompoundBorder(
                lblName.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblName);



        String[] country = {"USD", "LKR", "EUR", "JPY", "GBP"};
        cb1 = new JComboBox(country);
        cb1.setFont(new Font("", 1, 20));
        cb1.setBorder(BorderFactory.createCompoundBorder(
                cb1.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(cb1);

//=======to=========
        lblAddress = new JLabel("To");
        lblAddress.setFont(new Font("", 1, 20));
        lblAddress.setBorder(BorderFactory.createCompoundBorder(
                lblAddress.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblAddress);

        cb2 = new JComboBox(country);
        cb2.setFont(new Font("", 1, 20));
        cb2.setBorder(BorderFactory.createCompoundBorder(
                cb2.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(cb2);

//=======converted count======
        lblCount = new JLabel("00.00");
        lblCount.setFont(new Font("", 1, 40));
        // lblCount.setFont(new Color("#0000"));
        lblCount.setBorder(BorderFactory.createCompoundBorder(
                lblCount.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblCount);


        add("Center", centerPanel);
        pack();


        convertButton.addMouseListener(new convert());
        cancelButton.addMouseListener(new reset());
    }


    private Currency[] setRates() throws SQLException, ClassNotFoundException {

        ArrayList<Currency> allCurrency = getAllCurrency();
        Currency[] tempCurrencies = new Currency[5];

            for (int i=0;i<tempCurrencies.length;i++){
                tempCurrencies[i] = new Currency(
                            allCurrency.get(i).getCurrencyName(),
                            allCurrency.get(i).getUsdRate(),
                            allCurrency.get(i).getLkrRate(),
                            allCurrency.get(i).getEurRate(),
                            allCurrency.get(i).getJpyRate(),
                            allCurrency.get(i).getGbpRate());
            }

/* Currency c1 = new USD();
        c1.setCurrencyName("USD");
        c1.setUsdRate(1.0);
        c1.setLkrRate(0.0031);
        c1.setEurRate(1.08);
        c1.setJpyRate(0.0078);
        c1.setGbpRate(1.3021);

        Currency c2 = new LKR();
        c2.setCurrencyName("LKR");
        c2.setUsdRate(326.09);
        c2.setLkrRate(1.0);
        c2.setEurRate(351.3);
        c2.setJpyRate(2.55);
        c2.setGbpRate(422.56);

        Currency c3 = new EUR();
        c3.setCurrencyName("EUR");
        c3.setUsdRate(0.93);
        c3.setLkrRate(0.0029);
        c3.setEurRate(1.0);
        c3.setJpyRate(0.0072);
        c3.setGbpRate(1.20652);

        Currency c4 = new JPY();
        c4.setCurrencyName("JPY");
        c4.setUsdRate(128.12);
        c4.setLkrRate(0.39);
        c4.setEurRate(138.04);
        c4.setJpyRate(1.0);
        c4.setGbpRate(165.021);

        Currency c5 = new GBP();
        c5.setCurrencyName("GBP");
        c5.setUsdRate(0.76783);
        c5.setLkrRate(0.00234);
        c5.setEurRate(0.82864);
        c5.setJpyRate(0.00606);
        c5.setGbpRate(1.0);


        currencies[0] = c1;
        currencies[1] = c2;
        currencies[2] = c3;
        currencies[3] = c4;
        currencies[4] = c5;*/

        return tempCurrencies;
    }

    //======================get data from database======================
    private ArrayList<Currency> getAllCurrency() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/CurrencyDb",
                "root",
                "Lp1999#"
        );
        Statement stm = con.createStatement();
        String query = "SELECT * FROM Currency";
        ResultSet rst = stm.executeQuery(query);
        ArrayList<Currency> currencies = new ArrayList<>();

        while (rst.next()) {
            currencies.add(new Currency(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return currencies;
    }

    //======================mouse events one======================
    private class convert implements MouseListener {
        public void mouseEntered(MouseEvent evt) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                convertOnAction();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        //=======main function=======
        private void convertOnAction() throws SQLException, ClassNotFoundException {
            //enter the current currency rates
            Currency[] currencies = setRates();

            //get input values
            Double count = Double.valueOf(txtId.getText());
            String from = String.valueOf(cb1.getSelectedItem());
            String to = String.valueOf(cb2.getSelectedItem());

            Currency tempCurrency = null;
            for (int i = 0; i < currencies.length; i++) {
                if (currencies[i].getCurrencyName().equals(to)) {
                    tempCurrency = currencies[i];
                }
            }

            Double tempCount = 00.0;
            switch (from) {
                case "USD":
                    tempCount = tempCurrency.getUsdRate();
                    break;
                case "LKR":
                    tempCount = tempCurrency.getLkrRate();
                    break;
                case "EUR":
                    tempCount = tempCurrency.getEurRate();
                    break;
                case "JPY":
                    tempCount = tempCurrency.getJpyRate();
                    break;
                case "GBP":
                    tempCount = tempCurrency.getGbpRate();
                    break;
            }
            //System.out.println("currency : "+tempCurrency.getCurrencyName());
            //System.out.println("convert to : "+tempCount);
            // System.out.println("count  : "+count);
            float convertedCount = (float) (tempCount * count);
            System.out.println("value  : " + convertedCount);
            lblCount.setText(String.valueOf(convertedCount));
        }
    }

    //======================mouse events two======================

    private class reset implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            clearFields();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        private void clearFields() {
            System.out.println("clear call");
            lblCount.setText("00.00");
            txtId.setText("");
        }
    }
}

//======================main class======================
class Example {
    public static void main(String[] args) {
        new AddMoneyConverterForm().setVisible(true);
    }
}

//======================model classes======================
class Currency {
    private String currencyName;
    private Double usdRate;
    private Double lkrRate;
    private Double eurRate;
    private Double jpyRate;
    private Double gbpRate;

    public Currency(String currencyName, Double usdRate, Double lkrRate, Double eurRate, Double jpyRate, Double gbpRate) {
        this.currencyName = currencyName;
        this.usdRate = usdRate;
        this.lkrRate = lkrRate;
        this.eurRate = eurRate;
        this.jpyRate = jpyRate;
        this.gbpRate = gbpRate;
    }

    public Currency() {
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(Double usdRate) {
        this.usdRate = usdRate;
    }

    public Double getLkrRate() {
        return lkrRate;
    }

    public void setLkrRate(Double lkrRate) {
        this.lkrRate = lkrRate;
    }

    public Double getEurRate() {
        return eurRate;
    }

    public void setEurRate(Double eurRate) {
        this.eurRate = eurRate;
    }

    public Double getJpyRate() {
        return jpyRate;
    }

    public void setJpyRate(Double jpyRate) {
        this.jpyRate = jpyRate;
    }

    public Double getGbpRate() {
        return gbpRate;
    }

    public void setGbpRate(Double gbpRate) {
        this.gbpRate = gbpRate;
    }
}

class USD extends Currency {
}

class LKR extends Currency {
}

class EUR extends Currency {
}

class JPY extends Currency {
}

class GBP extends Currency {
}


        /*//===============new
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class AddMoneyConverterForm extends JFrame {
    private final JLabel titleLabel;
    private final JButton convertButton;
    private final JButton cancelButton;
    private final JLabel lblId;
    private final JLabel lblName;
    private final JLabel lblCount;
    private final JLabel lblAddress;
    private final JTextField txtId;
    JComboBox cb1;
    JComboBox cb2;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtSalary;
    private JLabel lblSalary;

    AddMoneyConverterForm() {
        setSize(400, 400);
        setTitle("Money Converter ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Money Converter ");
        titleLabel.setFont(new Font("", 1, 35));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                titleLabel.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add("North", titleLabel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        convertButton = new JButton("Convert");
        convertButton.setFont(new Font("", 1, 20));
        cancelButton = new JButton("Reset");
        cancelButton.setFont(new Font("", 1, 20));
        buttonPanel.add(convertButton);
        buttonPanel.add(cancelButton);
        add("South", buttonPanel);

//Adding center panel;
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 2));

//=======Amount=========
        lblId = new JLabel("Amount");
        lblId.setFont(new Font("", 1, 20));
        lblId.setBorder(BorderFactory.createCompoundBorder(
                lblId.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblId);
        txtId = new JTextField(20);
        txtId.setFont(new Font("", 1, 20));
        txtId.setBorder(BorderFactory.createCompoundBorder(
                txtId.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(txtId);

//=======from=========
        lblName = new JLabel("From");
        lblName.setFont(new Font("", 1, 20));
        lblName.setBorder(BorderFactory.createCompoundBorder(
                lblName.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblName);

        String[] country = {"USD", "LKR", "EUR", "JPY", "GBP"};
        cb1 = new JComboBox(country);
        cb1.setFont(new Font("", 1, 20));
        cb1.setBorder(BorderFactory.createCompoundBorder(
                cb1.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(cb1);

//=======to=========
        lblAddress = new JLabel("To");
        lblAddress.setFont(new Font("", 1, 20));
        lblAddress.setBorder(BorderFactory.createCompoundBorder(
                lblAddress.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblAddress);

        cb2 = new JComboBox(country);
        cb2.setFont(new Font("", 1, 20));
        cb2.setBorder(BorderFactory.createCompoundBorder(
                cb2.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(cb2);

//=======converted count======
        lblCount = new JLabel("00.00");
        lblCount.setFont(new Font("", 1, 40));
        // lblCount.setFont(new Color("#0000"));
        lblCount.setBorder(BorderFactory.createCompoundBorder(
                lblCount.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        centerPanel.add(lblCount);


        add("Center", centerPanel);
        pack();


        convertButton.addMouseListener(new convert());
        cancelButton.addMouseListener(new reset());
    }


    private Currency[] setRates() {
        Currency c1 = new USD();
        c1.setCurrencyName("USD");
        c1.setUsdRate(1.0);
        c1.setLkrRate(0.0031);
        c1.setEurRate(1.08);
        c1.setJpyRate(0.0078);
        c1.setGbpRate(1.3021);

        Currency c2 = new LKR();
        c2.setCurrencyName("LKR");
        c2.setUsdRate(326.09);
        c2.setLkrRate(1.0);
        c2.setEurRate(351.3);
        c2.setJpyRate(2.55);
        c2.setGbpRate(422.56);

        Currency c3 = new EUR();
        c3.setCurrencyName("EUR");
        c3.setUsdRate(0.93);
        c3.setLkrRate(0.0029);
        c3.setEurRate(1.0);
        c3.setJpyRate(0.0072);
        c3.setGbpRate(1.20652);

        Currency c4 = new JPY();
        c4.setCurrencyName("JPY");
        c4.setUsdRate(128.12);
        c4.setLkrRate(0.39);
        c4.setEurRate(138.04);
        c4.setJpyRate(1.0);
        c4.setGbpRate(165.021);

        Currency c5 = new GBP();
        c5.setCurrencyName("GBP");
        c5.setUsdRate(0.76783);
        c5.setLkrRate(0.00234);
        c5.setEurRate(0.82864);
        c5.setJpyRate(0.00606);
        c5.setGbpRate(1.0);

        Currency[] currencies = new Currency[5];
        currencies[0] = c1;
        currencies[1] = c2;
        currencies[2] = c3;
        currencies[3] = c4;
        currencies[4] = c5;
        return currencies;
    }

    //======================mouse events one======================
    private class convert implements MouseListener {
        @Override
        public void mouseEntered(MouseEvent evt) {


        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            convertOnAction();
            String S1,S2,S3,S4;
            S1=txtId.getText();
            S2=lblCount.getText();
            S3= (String)cb1.getSelectedItem();
            S4=(String)cb2.getSelectedItem();

            try{

                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                Connection con=DriverManager.getConnection("jdbc:ucanaccess://C:\\HNDIT\\Currency.accdb");
                Statement st=con.createStatement();
                st.execute("INSERT INTO Converter(Amount, Currency1, Currency2,Total)VALUES('"+S1+"','"+S3+"','"+S4+"','"+S2+"');");
                st.close();
                con.close();
                JOptionPane.showMessageDialog(null," Converted");
            }
            catch(Exception err)
            {
                JOptionPane.showMessageDialog(null,"Already Converted");
            }


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }





        //=======main function=======
        private void convertOnAction() {
            //enter the current currency rates
            Currency[] currencies = setRates();

            //get input values
            Double count = Double.valueOf(txtId.getText());
            String from = String.valueOf(cb1.getSelectedItem());
            String to = String.valueOf(cb2.getSelectedItem());

            Currency tempCurrency = null;
            for (int i = 0; i < currencies.length; i++) {
                if (currencies[i].getCurrencyName() == to) {
                    tempCurrency = currencies[i];
                }
            }

            Double tempCount = 00.0;
            switch (from) {
                case "USD":
                    tempCount = tempCurrency.getUsdRate();
                    break;
                case "LKR":
                    tempCount = tempCurrency.getLkrRate();
                    break;
                case "EUR":
                    tempCount = tempCurrency.getEurRate();
                    break;
                case "JPY":
                    tempCount = tempCurrency.getJpyRate();
                    break;
                case "GBP":
                    tempCount = tempCurrency.getGbpRate();
                    break;
            }
            //System.out.println("currency : "+tempCurrency.getCurrencyName());
            //System.out.println("convert to : "+tempCount);
            // System.out.println("count  : "+count);
            float convertedCount = (float) (tempCount * count);
            System.out.println("value  : " + convertedCount);
            lblCount.setText(String.valueOf(convertedCount));
        }

    }



    //======================mouse events two======================

    private class reset implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            clearFields();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        private void clearFields() {
            System.out.println("clear call");
            lblCount.setText("00.00");
            txtId.setText("");
        }
    }

}

//======================main class======================
class Example {
    public static void main(String[] args) {
        new AddMoneyConverterForm().setVisible(true);
    }
}

//======================model classes======================
class Currency {
    private String currencyName;
    private Double usdRate;
    private Double lkrRate;
    private Double eurRate;
    private Double jpyRate;
    private Double gbpRate;

    public Currency(String currencyName, Double usdRate, Double lkrRate, Double eurRate, Double jpyRate, Double gbpRate) {
        this.currencyName = currencyName;
        this.usdRate = usdRate;
        this.lkrRate = lkrRate;
        this.eurRate = eurRate;
        this.jpyRate = jpyRate;
        this.gbpRate = gbpRate;
    }

    public Currency() {
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(Double usdRate) {
        this.usdRate = usdRate;
    }

    public Double getLkrRate() {
        return lkrRate;
    }

    public void setLkrRate(Double lkrRate) {
        this.lkrRate = lkrRate;
    }

    public Double getEurRate() {
        return eurRate;
    }

    public void setEurRate(Double eurRate) {
        this.eurRate = eurRate;
    }

    public Double getJpyRate() {
        return jpyRate;
    }

    public void setJpyRate(Double jpyRate) {
        this.jpyRate = jpyRate;
    }

    public Double getGbpRate() {
        return gbpRate;
    }

    public void setGbpRate(Double gbpRate) {
        this.gbpRate = gbpRate;
    }
}

class USD extends Currency {
}

class LKR extends Currency {
}

class EUR extends Currency {
}

class JPY extends Currency {
}

class GBP extends Currency {
}*/