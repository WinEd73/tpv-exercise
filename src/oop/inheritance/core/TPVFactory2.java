package oop.inheritance.core;

import oop.inheritance.data.SupportedTerminal;
import oop.inheritance.ingenico.IngenicoPrinter;
import oop.inheritance.verifone.v240m.VerifoneV240mPrinter;
import oop.inheritance.verifone.vx520.VerifoneVx520Printer;
import oop.inheritance.verifone.vx690.VerifoneVx690Printer;


public class TPVFactory2 {


    public TPVPrint getInstance;
    private SupportedTerminal supportedTerminal;

    public TPVFactory2(SupportedTerminal supportedTerminal) {
        this.supportedTerminal = supportedTerminal;
    }
    public TPVPrint getInstance(){
        if (supportedTerminal == SupportedTerminal.INGENICO) {
            return new IngenicoPrinter();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_240) {
            return new VerifoneV240mPrinter();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_520) {
            return new VerifoneVx520Printer();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_690) {
            return new VerifoneVx690Printer();
        }
        return null;
    }
}
