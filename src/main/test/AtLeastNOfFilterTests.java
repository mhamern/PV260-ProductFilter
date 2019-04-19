import cz.muni.fi.pv260.productfilter.AtLeastNOfFilter;
import cz.muni.fi.pv260.productfilter.Color;
import cz.muni.fi.pv260.productfilter.Filter;
import cz.muni.fi.pv260.productfilter.FilterNeverSucceeds;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

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
        Filter<DummyValue> dummyFilter = new DummyAlwaysPassFilter();
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter<>(0, dummyFilter);
        });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void nIsNegativeValueCorrectExceptionThrown() {
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter(-1);
        });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nHigherThanChildrenCountCorrectExceptionThrown() {
        Filter<DummyValue> dummyFilter = new DummyAlwaysPassFilter();
        Throwable thrown = catchThrowable(() -> {
            AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter<>(2, dummyFilter);
        });
        assertThat(thrown).isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void filterPassesIfAllChildrenPassesSingle() {
        Filter<DummyValue> dummyFilter = new DummyAlwaysPassFilter();
        AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter<>(1, dummyFilter);
        assertThat(atLeastNOfFilter.passes(DummyValue.FIRST_VALUE)).isTrue();
    }

    @Test
    public void filterPassesIfAllChildrenPassesMultiple() {
        Filter<DummyValue> dummyFilter = new DummyAlwaysPassFilter();
        Filter mockedFilter = mock(Filter.class);
        when(mockedFilter.passes(DummyValue.FIRST_VALUE)).thenReturn( true);

        AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter<DummyValue>(2, dummyFilter, mockedFilter);
        assertThat(atLeastNOfFilter.passes(DummyValue.FIRST_VALUE)).isTrue();
        verify(mockedFilter, times(1)).passes(DummyValue.FIRST_VALUE);
    }

    @Test
    public void filterFailsIfSomeChildFails() {
        Filter<DummyValue> dummyFilter = new DummyAlwaysPassFilter();
        Filter mockedFilter = mock(Filter.class);
        when(mockedFilter.passes(DummyValue.FIRST_VALUE)).thenReturn( false);

        AtLeastNOfFilter<DummyValue> atLeastNOfFilter = new AtLeastNOfFilter<DummyValue>(2, dummyFilter, mockedFilter);
        assertThat(atLeastNOfFilter.passes(DummyValue.FIRST_VALUE)).isFalse();
        verify(mockedFilter, times(1)).passes(DummyValue.FIRST_VALUE);
    }
}

