import cz.muni.fi.pv260.productfilter.AtLeastNOfFilter;
import cz.muni.fi.pv260.productfilter.Color;
import cz.muni.fi.pv260.productfilter.Filter;
import cz.muni.fi.pv260.productfilter.FilterNeverSucceeds;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class AtLeastNOfFilterTests {

    @Test
    public void nIsZeroCorrectExceptionThrown() {
        Throwable thrown = catchThrowable(() -> {
                    AtLeastNOfFilter atLeastNOfFilter = new AtLeastNOfFilter(0);
        });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nIsZeroAndFilterPassedCorrectExceptionThrown() {
        Filter<Color> dummyFilter = new DummyAlwaysPassColorFilter();
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter atLeastNOfFilter = new AtLeastNOfFilter(0, dummyFilter);
        });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void nIsNegativeValueCorrectExceptionThrown() {
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter atLeastNOfFilter = new AtLeastNOfFilter(-1);
        });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nHigherThanChildrenCountCorrectExceptionThrown() {
        Filter<Color> dummyFilter = new DummyAlwaysPassColorFilter();
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter atLeastNOfFilter = new AtLeastNOfFilter(2, dummyFilter);
        });
        assertThat(thrown).isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void filterPassesIfAllChildrenPasses() {

    }

    @Test
    public void filterFailsIfSomeChildFails() {

    }
}

