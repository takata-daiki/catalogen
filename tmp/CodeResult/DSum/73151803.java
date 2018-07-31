package loesung;

import java.io.Serializable;

import okoelopoly.Individuum;
import okoelopoly.Punktverteilung;

public class MeinIndividuum implements Individuum, Serializable {

    private static final long serialVersionUID = 1169885659413200081L;
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;

    public MeinIndividuum(double a, double b, double c, double d, double e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public void wendeDieStrategieAn(Punktverteilung simulatorstatus) {
        int ap = simulatorstatus.getAktionspunkte();
        double asum = 0.0;
        double bsum = 0.0;
        double csum = 0.0;
        double dsum = 0.0;
        double esum = 0.0;
        while (ap > 0) {
            asum += a;
            if (asum >= 1.0 && ap > 0) {
                simulatorstatus.investiereInAufklaerung(1);
                asum -= 1.0;
                ap--;
            }
            bsum += b;
            if (bsum >= 1.0 && ap > 0) {
                simulatorstatus.investiereInLebensqualitaet(1);
                bsum -= 1.0;
                ap--;
            }
            csum += c;
            if (csum >= 1.0 && ap > 0) {
                simulatorstatus.investiereInProduktion(1);
                csum -= 1.0;
                ap--;
            }
            dsum += d;
            if (dsum >= 1.0 && ap > 0) {
                simulatorstatus.investiereInSanierung(1);
                dsum -= 1.0;
                ap--;
            }
            esum += e;
            if (esum >= 1.0 && ap > 0) {
                simulatorstatus.investiereInVermehrungsrate(1);
                esum -= 1.0;
                ap--;
            }

        }
    }

}
