/*package com.shiel.campaignapi.service;

import com.shiel.campaignapi.dto.PaymentGatewayResponse;
import com.shiel.campaignapi.dto.PaymentNotificationDto;
import com.shiel.campaignapi.dto.PaymentRequestDto;
import com.shiel.campaignapi.entity.Booking;
import com.shiel.campaignapi.entity.PaymentDetails;
import com.shiel.campaignapi.repository.BookingRepository;
import com.shiel.campaignapi.repository.PaymentDetailsRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
public class PaymentService {

	@Autowired
	private final RestTemplate restTemplate;
	private final PaymentDetailsRepository paymentDetailsRepository;
	private final BookingRepository bookingRepository;

	public PaymentService(RestTemplate restTemplate, PaymentDetailsRepository paymentDetailsRepository,
			BookingRepository bookingRepository) {
		this.restTemplate = restTemplate;
		this.paymentDetailsRepository = paymentDetailsRepository;
		this.bookingRepository = bookingRepository;
	}

	private final String NOTIFY_URL = "https://www.cashfree.com/devstudio/preview/pg/mobile/android?order_id={order_id}";
	private final String RETURN_URL = "https://www.cashfree.com/devstudio/preview/pg/webhooks/7501856";

	@Value("${cashfree.app.id}")
	private String APP_ID;
	@Value("${cashfree.api.base.url}")
	private String PAYMENT_GATEWAY_URL;
	@Value("${cashfree.secret.key}")
	private String SECRET_KEY;

	public PaymentGatewayResponse initiatePayment(PaymentRequestDto paymentRequestDto) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-api-version", "2022-09-01");
		headers.set("x-client-id", APP_ID);
		headers.set("x-client-secret", SECRET_KEY);

		HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(paymentRequestDto, headers);

		ResponseEntity<PaymentGatewayResponse> responseEntity = restTemplate.exchange(
				PAYMENT_GATEWAY_URL,
				HttpMethod.POST, 
				entity, 
				PaymentGatewayResponse.class
			);

		return responseEntity.getBody();
	}

	@Transactional
	public void handlePaymentNotification(PaymentNotificationDto paymentNotification) {
		String bookingId = paymentNotification.getBookingId();
		String transactionId = paymentNotification.getTransactionId();
		String paymentStatus = paymentNotification.getStatus();

		if ("SUCCESS".equalsIgnoreCase(paymentStatus)) {
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setBookingId(bookingId);
			paymentDetails.setTransactionId(transactionId);
			paymentDetails.setPaymentMethod(paymentNotification.getPaymentMethod());
			paymentDetails.setPaymentStatus(paymentStatus);
			paymentDetails.setTotalAmount(paymentNotification.getAmount());
			paymentDetails.setCurrency(paymentNotification.getCurrency());
			paymentDetails.setPaymentTime(paymentNotification.getPaymentTime());
			paymentDetails.setCustomerName(paymentNotification.getCustomerName());
			paymentDetails.setCustomerEmail(paymentNotification.getCustomerEmail());
			paymentDetails.setCustomerPhone(paymentNotification.getCustomerPhone());

			paymentDetailsRepository.save(paymentDetails);

			Booking booking = bookingRepository.findByBookingId(Long.parseLong(bookingId))
					.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

			booking.setBookingStatus(Booking.BookingStatus.COMPLETED);
			bookingRepository.save(booking);
		} else {
			Booking booking = bookingRepository.findByBookingId(Long.parseLong(bookingId))
					.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

			booking.setBookingStatus(Booking.BookingStatus.REJECTED);
			bookingRepository.save(booking);
		}
	}

	public String getNotifyUrl() {
		return NOTIFY_URL;
	}

	public String getReturnUrl() {
		return RETURN_URL;
	}
}*/
