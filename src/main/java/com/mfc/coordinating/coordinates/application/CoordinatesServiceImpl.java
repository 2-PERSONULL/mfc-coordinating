package com.mfc.coordinating.coordinates.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.coordinates.domain.Coordinates;
import com.mfc.coordinating.coordinates.domain.CoordinatesImage;
import com.mfc.coordinating.coordinates.dto.kafka.CoordinatesSubmittedEventDto;
import com.mfc.coordinating.coordinates.dto.request.CoordinatesRequest;
import com.mfc.coordinating.coordinates.dto.response.CoordinatesResponse;
import com.mfc.coordinating.coordinates.infrastructure.CoordinatesImageRepository;
import com.mfc.coordinating.coordinates.infrastructure.CoordinatesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoordinatesServiceImpl implements CoordinatesService {
	private final CoordinatesRepository coordinatesRepository;
	private final CoordinatesImageRepository coordinatesImageRepository;
	private final KafkaTemplate<String, CoordinatesSubmittedEventDto> kafkaTemplate;

	private static final String COORDINATES_SUBMITTED_TOPIC = "coordinates-submitted-topic";

	@Override
	@Transactional
	public List<Long> createCoordinates(List<CoordinatesRequest> requests, String partnerUuid) {
		List<Coordinates> savedCoordinates = saveCoordinates(requests, partnerUuid);
		saveCoordinatesImages(savedCoordinates, requests);
		sendCoordinatesSubmittedEvent(requests.get(0).getRequestId(), partnerUuid);

		return savedCoordinates.stream()
			.map(Coordinates::getId)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CoordinatesResponse> getCoordinatesByRequestId(String requestId) {
		List<Coordinates> coordinatesList = coordinatesRepository.findByRequestId(requestId);
		if (coordinatesList.isEmpty()) {
			throw new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND);
		}
		return coordinatesList.stream()
			.map(this::mapToCoordinatesResponse)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateCoordinates(Long coordinateId, CoordinatesRequest request) {
		Coordinates coordinates = findCoordinatesById(coordinateId);
		coordinates.update(
			request.getCategory(),
			request.getBrand(),
			request.getBudget(),
			request.getUrl(),
			request.getComment()
		);
		saveCoordinatesImages(coordinates, request.getImages());
	}

	@Override
	@Transactional
	public void deleteCoordinates(Long coordinateId) {
		if (!coordinatesRepository.existsById(coordinateId)) {
			throw new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND);
		}
		coordinatesRepository.deleteById(coordinateId);
	}

	private List<Coordinates> saveCoordinates(List<CoordinatesRequest> requests, String partnerUuid) {
		List<Coordinates> coordinatesList = requests.stream()
			.map(request -> mapToCoordinates(request, partnerUuid))
			.collect(Collectors.toList());
		return coordinatesRepository.saveAll(coordinatesList);
	}

	private void saveCoordinatesImages(List<Coordinates> coordinates, List<CoordinatesRequest> requests) {
		for (int i = 0; i < coordinates.size(); i++) {
			saveCoordinatesImages(coordinates.get(i), requests.get(i).getImages());
		}
	}

	private void saveCoordinatesImages(Coordinates coordinates, List<String> imageUrls) {
		coordinatesImageRepository.deleteByCoordinatesId(coordinates.getId());
		List<CoordinatesImage> coordinatesImages = imageUrls.stream()
			.map(imageUrl -> CoordinatesImage.builder()
				.imageUrl(imageUrl)
				.coordinates(coordinates)
				.build())
			.collect(Collectors.toList());
		coordinatesImageRepository.saveAll(coordinatesImages);
	}

	private Coordinates mapToCoordinates(CoordinatesRequest request, String partnerUuid) {
		return Coordinates.builder()
			.category(request.getCategory())
			.brand(request.getBrand())
			.budget(request.getBudget())
			.url(request.getUrl())
			.comment(request.getComment())
			.requestId(request.getRequestId())
			.partnerId(partnerUuid)
			.build();
	}

	private CoordinatesResponse mapToCoordinatesResponse(Coordinates coordinates) {
		List<CoordinatesImage> images = coordinatesImageRepository.findByCoordinatesId(coordinates.getId());
		return CoordinatesResponse.from(coordinates, images);
	}

	private void sendCoordinatesSubmittedEvent(String requestId, String partnerUuid) {
		CoordinatesSubmittedEventDto event = new CoordinatesSubmittedEventDto(requestId, partnerUuid);
		kafkaTemplate.send(COORDINATES_SUBMITTED_TOPIC, event);
	}

	private Coordinates findCoordinatesById(Long coordinateId) {
		return coordinatesRepository.findById(coordinateId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND));
	}
}