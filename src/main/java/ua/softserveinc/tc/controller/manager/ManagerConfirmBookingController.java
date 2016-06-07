package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Петришак on 08.05.2016.
 */
@Controller
public class ManagerConfirmBookingController {
    @Autowired
    BookingDao bookingDao;
    @Autowired
    ChildService child;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW)
    public ModelAndView listBookings(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        Room currentRoom = roomService.getRoomByManager(currentManager);
        List<Booking> listBooking = bookingService.getTodayBookingsByRoom(currentRoom);
        model.addAttribute(BookingConstants.Model.LIST_BOOKINGS, listBooking);
        return modelAndView;
    }

    @RequestMapping(value = BookingConstants.Model.CANCEL_BOOKING, method = RequestMethod.GET)
    public @ResponseBody String cancelBooking (@PathVariable Long idBooking) {
        Booking booking = bookingService.findById(idBooking);
        booking.setBookingState(BookingState.CANCELLED);
        booking.setSum(0L);
        bookingService.update(booking);
        BookingDTO bookingDTO = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTO);
    }

    @RequestMapping(value = BookingConstants.Model.SET_START_TIME, method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookingsStartTime(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.confirmBookingStartTime(bookingDTO);
        booking.setBookingState(BookingState.ACTIVE);
        bookingService.update(booking);
        BookingDTO bookingDTOtoJson = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

    @RequestMapping(value = BookingConstants.Model.SET_END_TIME, method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookingsEndTime(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.confirmBookingEndTime(bookingDTO);
        booking.setBookingState(BookingState.COMPLETED);
        bookingService.update(booking);
        BookingDTO bookingDTOtoJson = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

     @RequestMapping(value = BookingConstants.Model.LIST_BOOKING, method = RequestMethod.GET)
     @ResponseBody
     public String listBookigs(Principal principal) {
         User currentManager = userService.getUserByEmail(principal.getName());
         Room roomCurrentManager = roomService.getRoomByManager(currentManager);
         List<Booking> listBooking = bookingService.getTodayBookingsByRoom(roomCurrentManager);
         List<BookingDTO> listBookingDTO = new ArrayList<BookingDTO>();
         listBooking.forEach(booking -> listBookingDTO.add(new BookingDTO(booking)));
         Gson gson = new Gson();
         return  gson.toJson(listBookingDTO);
    }

     @RequestMapping(value = BookingConstants.Model.BOOK_DURATION, method = RequestMethod.POST, consumes = "application/json")
     @ResponseBody
     public String bookinkDuration(@RequestBody BookingDTO bookingDTO) throws ParseException{
         Booking booking = bookingService.findById(bookingDTO.getId());
         Date date = bookingService.getDateAndTimeBooking(booking, bookingDTO.getEndTime());
         booking.setBookingEndTime(date);
         bookingService.calculateAndSetDuration(booking);
         BookingDTO bookingDTOtoJson = new BookingDTO(booking);
         Gson gson = new Gson();
         return  gson.toJson(bookingDTOtoJson);
    }

}