package game;

import game.objects.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HexagonTest {
    Hexagon hexagon = Mockito.mock(Hexagon.class);
    Unit unit = Mockito.mock(Unit.class);

    @Test
    public void shouldCreateHexagon() {
        Assertions.assertNotNull(hexagon);
    }

    @Test
    public void shouldSetUnit() {

        hexagon.setUnit(unit);

        Mockito.verify(hexagon, Mockito.times(1)).setUnit(unit);
    }

    @Test
    public void shouldClear() {

        hexagon.setUnit(unit);
        hexagon.clear();

        Mockito.verify(hexagon, Mockito.times(1)).clear();
    }

    @Test
    public void shouldMove() {
        Hexagon hexagonFrom = Mockito.mock(Hexagon.class);
        Hexagon hexagonTo = Mockito.mock(Hexagon.class);

        hexagonFrom.setUnit(unit);
        hexagonTo.move(hexagonFrom);

        Mockito.verify(hexagonTo, Mockito.times(1)).move(hexagonFrom);
    }
}
