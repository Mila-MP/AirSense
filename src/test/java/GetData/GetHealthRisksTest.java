package GetData;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetHealthRisksTest {

    @Test
    void printCO() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("CO");
        assertEquals("Carbon Monoxide is a colourless, odourless poisonous gas produced" +
                        " by incomplete, or inefficient, combustion of fuel including 'cold' or badly tuned" +
                        " engines. The gas affects the transport of oxygen around the body by the blood. At very" +
                        " high levels, this can lead to a significant reduction in the supply of oxygen to the" +
                        " heart, particularly in people suffering from heart disease.",
                healthRisk.print());
    }

    @Test
    void printNO2() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("NO2");
        assertEquals("Nitrogen oxides are formed during high temperature combustion processes from the" +
                        " oxidation of nitrogen in the air or fuel. Nitrogen Dioxide has several health impacts and" +
                        " includes general irritation to the eyes, irritation of the respiratory system and shortness" +
                        " of breath.",
                healthRisk.print());
    }

    @Test
    void printO3() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("O3");
        assertEquals("Ozone is not directly emitted, but is formed by a complex set of reactions involving" +
                        " nitrogen oxides and hydrocarbons in the presence of sunlight. Like nitrogen dioxide, high" +
                        " levels of ozone can irritate and inflame the lungs. It can also cause eye irritation," +
                        " migraine and coughing.",
                healthRisk.print());
    }

    @Test
    void printPM10() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("PM10");
        assertEquals("Larger particles in the atmosphere are generally filtered in the nose and throat and" +
                        " do not cause problems, but particulate matter smaller than about 10 micrometers, referred" +
                        " to as PM10, can settle in the bronchi and lungs and cause health problems. The effects of" +
                        " inhaling particulate matter have been widely studied in humans and animals and include" +
                        " asthma, respiratory problems and cardiovascular issues.",
                healthRisk.print());
    }

    @Test
    void printPM25() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("PM25");
        assertEquals("These are particles which are less than 2.5 micrometers (a thousandth of a millimetre)" +
                        " in diameter. Particles are made up of many different chemicals, including toxic metals and" +
                        " organic compounds, some of which are highly chemically reactive and researchers consider" +
                        " these to be involved in the toxicological effects of particles. They are thought to have" +
                        " a greater effect on health than larger particles as they can be carried deeper into the" +
                        " lungs where they can cause inflammation and worsen heart and lung diseases.",
                healthRisk.print());
    }

    @Test
    void printSO2() throws IOException {
        GetHealthRisks healthRisk = new GetHealthRisks("SO2");
        assertEquals("Sulphur Dioxide is produced when a material or fuel containing sulphur is burned." +
                        " Pollution episodes only generally occur where there is widespread domestic use of coal or" +
                        " in the vicinity of coal or oil-fired power stations. Short-term exposure to high levels" +
                        " of sulphur dioxide may cause coughing, tightening of the chest and irritation of the lungs.",
                healthRisk.print());
    }
}
