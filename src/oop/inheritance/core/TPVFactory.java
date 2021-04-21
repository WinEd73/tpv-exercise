package oop.inheritance.core;

import oop.inheritance.data.SupportedTerminal;
import oop.inheritance.ingenico.IngenicoDisplay;
import oop.inheritance.verifone.v240m.VerifoneV240mDisplay;
import oop.inheritance.verifone.vx520.VerifoneVx520Display;
import oop.inheritance.verifone.vx690.VerifoneVx690Display;
//42:32
public class TPVFactory {

    private SupportedTerminal supportedTerminal;

    public TPVFactory(SupportedTerminal supportedTerminal) {
        this.supportedTerminal = supportedTerminal;
    }

    public TPVDisplay getDisplayInstance() {
        if (supportedTerminal == SupportedTerminal.INGENICO) {
            return new IngenicoDisplay();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_240) {
            return new VerifoneV240mDisplay();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_520) {
            return new VerifoneVx520Display();

        } else if (supportedTerminal == SupportedTerminal.VERIFONE_690) {
            return new VerifoneVx690Display();
        }

        return null;
    }
}
