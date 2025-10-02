package org.example.book_my_show.controllers;


import org.example.book_my_show.dtos.ConfirmPaymentRequestDto;
import org.example.book_my_show.dtos.ConfirmPaymentResponsDto;
import org.example.book_my_show.dtos.InitiatePaymentRequestDto;
import org.example.book_my_show.dtos.InitiatePaymentResponseDto;
import org.example.book_my_show.exceptions.InvalidTicketException;
import org.example.book_my_show.models.Payment;
import org.example.book_my_show.models.ResponseStatus;
import org.example.book_my_show.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public InitiatePaymentResponseDto intitiatePayment(@RequestBody InitiatePaymentRequestDto requestDto){
        InitiatePaymentResponseDto responseDto = new InitiatePaymentResponseDto();
        try {
            Payment payment = paymentService.initiatePayment(requestDto.getTicketId(), requestDto.getAmountl(), requestDto.getPaymentMode());
            responseDto.setAmount(payment.getAmount());
            responseDto.setPaymentMode(payment.getMode());
            responseDto.setPaymentProvider(payment.getPaymentProvider());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("Payment initiated successfully");
            return responseDto;
        }catch (Exception  e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }
    }

    public ConfirmPaymentResponsDto confirmPayment(@RequestBody ConfirmPaymentRequestDto requestDto){
        ConfirmPaymentResponsDto responseDto = new ConfirmPaymentResponsDto();
        try {
            Payment payment = paymentService.confirmPayment(requestDto.getPaymentId(), requestDto.getPaymentStatus());
            responseDto.setAmount(payment.getAmount());
            responseDto.setPaymentId(payment.getId());
            responseDto.setPaymentStatus(payment.getPaymentStatus());
            responseDto.setTicketId(payment.getTicket().getId());
            responseDto.setReferenceNumber(payment.getReferenceNumber());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setMessage("Payment confirmed successfully");
            return responseDto;
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }
    }
}
