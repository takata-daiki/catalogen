package by.dev.ksergienya.restaurant.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import by.dev.ksergienya.restaurant.model.Booking;
import by.dev.ksergienya.restaurant.model.Table;
import by.dev.ksergienya.restaurant.model.ui.SearchTableBean;
import by.dev.ksergienya.restaurant.service.exceptions.ServiceException;
import by.dev.ksergienya.restaurant.util.validators.SearchTableBeanValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("booking")
public class BookingController extends BaseController {
    private static final Logger log = Logger.getLogger(BookingController.class);
    private SearchTableBeanValidator searchTableBeanValidator = new SearchTableBeanValidator();

    @Override
    protected Logger getLogger() {
        return log;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView news() throws ServiceException {
        ModelAndView result = new ModelAndView("booking/stage1");

        result.addObject("searchTable", new SearchTableBean());

        return result;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView news(@ModelAttribute("searchTable") SearchTableBean searchTableBean,
                             BindingResult errors) throws ServiceException, ParseException {
        searchTableBeanValidator.validate(searchTableBean, errors);
        if (errors.hasErrors()) {
            return new ModelAndView("booking/stage1");
        }

        ModelAndView result = new ModelAndView("booking/stage2");
        List<Table> tables = tablesService.searchTables(searchTableBean);
        List<Booking> bookings = tablesService.searchBookingsForTime(new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(searchTableBean.getDateTime()));

        Iterator<Table> tableIterator = tables.iterator();
        while (tableIterator.hasNext()) {
            Table table = tableIterator.next();

            for (Booking booking : bookings) {
                if (booking.getTable().getId().equals(table.getId())) {
                    tableIterator.remove();
                    break;
                }
            }
        }

        result.addObject("tables", tables);
        result.addObject("searchTableBean", searchTableBean);

        return result;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, params = {"finish"})
    public ModelAndView finishBooking(@ModelAttribute("tableId") Integer tableId,
                                      @ModelAttribute("dateTime") String dateTime)
            throws ServiceException, ParseException {
        tablesService.createBooking(tableId, dateTime, getUserFromSession().getId());
        return new ModelAndView("redirect:/booking/my");
    }

    @RequestMapping(value = "my")
    public ModelAndView myBookings() throws ServiceException {
        ModelAndView result = new ModelAndView("booking/bookings");
        List<Booking> bookings = tablesService.searchBookingsForUser(getUserFromSession().getId());

        result.addObject("bookings", bookings);

        return result;
    }
}
