package pl.example.components.offer.hotel.booking;

import java.time.LocalDateTime;

public class HotelBookingDto {

	private Long id;
	private Long hotelId;
	private Long roomId;
	private LocalDateTime startBookingRoom;
	private LocalDateTime endBookingRoom;
	private float bookingPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public LocalDateTime getStartBookingRoom() {
		return startBookingRoom;
	}
	public void setStartBookingRoom(LocalDateTime startBookingRoom) {
		this.startBookingRoom = startBookingRoom;
	}
	public LocalDateTime getEndBookingRoom() {
		return endBookingRoom;
	}
	public void setEndBookingRoom(LocalDateTime endBookingRoom) {
		this.endBookingRoom = endBookingRoom;
	}
	public float getBookingPrice() {
		return bookingPrice;
	}
	public void setBookingPrice(float bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
}
