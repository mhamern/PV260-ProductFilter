import cz.muni.fi.pv260.productfilter.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static cz.muni.fi.pv260.productfilter.Controller.TAG_CONTROLLER;
import static org.mockito.Mockito.*;

public class ControllerTests {

    @Test
    public void sendsFilteredProductsToOutput() throws Exception {
        Input in = mock(Input.class);
        Collection<Product> products = Collections.singletonList(createDummyProduct());
        Output out = mock(Output.class);
        Logger logger = mock(Logger.class);
        Filter<Product> filter = new DummyAlwaysPassProductFilter();
        when(in.obtainProducts()).thenReturn(products);

        Controller controller = new Controller(in, out, logger);
        controller.select(filter);
        verify(out, times(1)).postSelectedProducts(products);
    }


    @Test
    public void logsMessageOnSuccess() throws Exception {
        Input in = mock(Input.class);
        Collection<Product> products = Collections.singletonList(createDummyProduct());
        Output out = mock(Output.class);
        Logger logger = mock(Logger.class);
        Filter<Product> filter = new DummyAlwaysPassProductFilter();
        when(in.obtainProducts()).thenReturn(products);

        Controller controller = new Controller(in, out, logger);
        controller.select(filter);
        verify(logger, times(1)).setLevel("INFO");
        verify(logger, times(1)).log(TAG_CONTROLLER,
                "Successfully selected 1 out of 1 available products.");
    }

    @Test
    public void exceptionIsLogged() throws ObtainFailedException {
        Input in = mock(Input.class);
        Output out = mock(Output.class);
        Logger logger = mock(Logger.class);
        Filter<Product> filter = new DummyAlwaysPassProductFilter();
        ObtainFailedException e = new ObtainFailedException();
        when(in.obtainProducts()).thenThrow(e);

        Controller controller = new Controller(in, out, logger);
        controller.select(filter);
        verify(logger, times(1)).setLevel("ERROR");
        verify(logger, times(1)).log(TAG_CONTROLLER,
                "Filter procedure failed with exception: " + e);
    }

    @Test
    public void exceptionIsNotPassedOn() throws ObtainFailedException {
        Input in = mock(Input.class);
        Output out = mock(Output.class);
        Logger logger = mock(Logger.class);
        Filter<Product> filter = new DummyAlwaysPassProductFilter();
        ObtainFailedException e = new ObtainFailedException();
        when(in.obtainProducts()).thenThrow(e);

        Controller controller = new Controller(in, out, logger);
        controller.select(filter);
        verify(out, never());
    }

    private Product createDummyProduct() {
        return new Product(1, "Product", Color.RED, BigDecimal.TEN);
    }
}
