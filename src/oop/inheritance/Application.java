package oop.inheritance;

import java.time.LocalDateTime;

import oop.inheritance.core.TPVDisplay;
import oop.inheritance.core.TPVFactory;
import oop.inheritance.core.TPVFactory2;
import oop.inheritance.core.TPVPrint;
import oop.inheritance.data.Card;
import oop.inheritance.data.CommunicationType;
import oop.inheritance.data.SupportedTerminal;
import oop.inheritance.data.Transaction;
import oop.inheritance.data.TransactionResponse;
import oop.inheritance.ingenico.IngenicoCardSwipper;
import oop.inheritance.ingenico.IngenicoChipReader;
import oop.inheritance.ingenico.IngenicoDisplay;
import oop.inheritance.ingenico.IngenicoEthernet;
import oop.inheritance.ingenico.IngenicoGPS;
import oop.inheritance.ingenico.IngenicoKeyboard;
import oop.inheritance.ingenico.IngenicoModem;
import oop.inheritance.ingenico.IngenicoPrinter;
import oop.inheritance.verifone.v240m.VerifoneV240mDisplay;

public class Application {

    private CommunicationType communicationType = CommunicationType.ETHERNET;
    private SupportedTerminal supportedTerminal;
    private TPVFactory tpvFactory;
    private TPVFactory2 tpvFactory2;

    public Application(SupportedTerminal supportedTerminal)
    {
        this.supportedTerminal = supportedTerminal;

        tpvFactory = new TPVFactory(supportedTerminal);
        tpvFactory2 = new TPVFactory2(supportedTerminal);
    }

    public void showMenu() {
        if (supportedTerminal == SupportedTerminal.INGENICO) {
            IngenicoDisplay ingenicoDisplay = new IngenicoDisplay();

            ingenicoDisplay.showMessage(5, 5, "MENU");
            ingenicoDisplay.showMessage(5, 10, "1. VENTA");
            ingenicoDisplay.showMessage(5, 13, "2. DEVOLUCION");
            ingenicoDisplay.showMessage(5, 16, "3. REPORTE");
            ingenicoDisplay.showMessage(5, 23, "4. CONFIGURACION");
        } else {
            VerifoneV240mDisplay verifoneV240mDisplay = new VerifoneV240mDisplay();

            verifoneV240mDisplay.showMessage(5, 5, "MENU");
            verifoneV240mDisplay.showMessage(5, 10, "1. VENTA");
            verifoneV240mDisplay.showMessage(5, 13, "2. DEVOLUCION");
            verifoneV240mDisplay.showMessage(5, 16, "3. REPORTE");
            verifoneV240mDisplay.showMessage(5, 23, "4. CONFIGURACION");
        }

    }

    public String readKey() {
        IngenicoKeyboard ingenicoKeyboard = new IngenicoKeyboard();

        return ingenicoKeyboard.get();
    }

    public void doSale() {
        IngenicoCardSwipper cardSwipper = new IngenicoCardSwipper();
        IngenicoChipReader chipReader = new IngenicoChipReader();
        IngenicoDisplay ingenicoDisplay = new IngenicoDisplay();
        IngenicoKeyboard ingenicoKeyboard = new IngenicoKeyboard();
        Card card;

        do {
            card = cardSwipper.readCard();
            if (card == null) {
                card = chipReader.readCard();
            }
        } while (card == null);

        ingenicoDisplay.clear();
        ingenicoDisplay.showMessage(5, 20, "Capture monto:");

        String amount = ingenicoKeyboard.get(); //Amount with decimal point as string

        Transaction transaction = new Transaction();

        transaction.setLocalDateTime(LocalDateTime.now());
        transaction.setCard(card);
        transaction.setAmountInCents(Integer.parseInt(amount.replace(".", "")));

        TransactionResponse response = sendSale(transaction);

        if (response.isApproved()) {
            ingenicoDisplay.showMessage(5, 25, "APROBADA");
            printReceipt(transaction, response.getHostReference());
        } else {
            ingenicoDisplay.showMessage(5, 25, "DENEGADA");
        }
    }
    //aki
    private void printReceipt(Transaction transaction, String hostReference) {
        TPVPrint tpvPrint = tpvFactory2.getInstance;
        Card card = transaction.getCard();

        TPVPrint.print(5, "APROBADA");
        TPVPrint.lineFeed();
        TPVPrint.print(5, card.getAccount());
        TPVPrint.lineFeed();
        TPVPrint.print(5, "" + transaction.getAmountInCents());
        TPVPrint.lineFeed();
        TPVPrint.print(5, "________________");

    }

    private TransactionResponse sendSale(Transaction transaction) {
        IngenicoEthernet ethernet = new IngenicoEthernet();
        IngenicoModem modem = new IngenicoModem();
        IngenicoGPS gps = new IngenicoGPS();
        TransactionResponse transactionResponse = null;

        switch (communicationType) {
            case ETHERNET:
                ethernet.open();
                ethernet.send(transaction);
                transactionResponse = ethernet.receive();
                ethernet.close();
                break;
            case GPS:
                gps.open();
                gps.send(transaction);
                transactionResponse = gps.receive();
                gps.close();
                break;
            case MODEM:
                modem.open();
                modem.send(transaction);
                transactionResponse = modem.receive();
                modem.close();
                break;
        }

        return transactionResponse;
    }

    public void doRefund() {
    }

    public void printReport() {
    }

    public void showConfiguration() {
    }

    public void clearScreen() {
        TPVDisplay tpvDisplay = tpvFactory.getDisplayInstance();

            tpvDisplay.clear();
            tpvDisplay.clear();
            tpvDisplay.clear();
            tpvDisplay.clear();

        }
    }

