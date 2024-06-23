package com.mfc.coordinating.coordinates.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.coordinating.common.exception.BaseException;
import com.mfc.coordinating.common.response.BaseResponseStatus;
import com.mfc.coordinating.coordinates.domain.Coordinates;
import com.mfc.coordinating.coordinates.domain.CoordinatesImage;
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
	//private final KafkaTemplate<String, Object> kafkaTemplate;


	@Override
	@Transactional
	public List<Long> createCoordinates(List<CoordinatesRequest> requests) {
		List<Coordinates> coordinatesList = requests.stream()
			.map(this::mapToCoordinates)
			.collect(Collectors.toList());

		List<Coordinates> savedCoordinates = coordinatesRepository.saveAll(coordinatesList);

		for (int i = 0; i < savedCoordinates.size(); i++) {
			Coordinates coordinates = savedCoordinates.get(i);
			CoordinatesRequest request = requests.get(i);
			saveCoordinatesImages(coordinates, request.getImages());
		}

		String requestId = requests.get(0).getRequestId();
		//kafkaTemplate.send("coordinates-submitted", String.valueOf(requestId));

		return savedCoordinates.stream()
			.map(Coordinates::getId)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public CoordinatesResponse getCoordinatesById(String id) {
		Coordinates coordinates = findCoordinatesByRequestId(id);
		List<CoordinatesImage> coordinatesImages = coordinatesImageRepository.findByCoordinatesId(coordinates.getId());
		return CoordinatesResponse.from(coordinates, coordinatesImages);
	}

	@Override
	@Transactional
	public void updateCoordinates(String id, CoordinatesRequest request) {
		Coordinates coordinates = findCoordinatesByRequestId(id);
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
	public void deleteCoordinates(String id) {
		coordinatesRepository.deleteByRequestId(id);
	}

	private Coordinates findCoordinatesByRequestId(String id) {
		return coordinatesRepository.findByRequestId(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND));
	}

	private Coordinates mapToCoordinates(CoordinatesRequest request) {
		return Coordinates.builder()
			.category(request.getCategory())
			.brand(request.getBrand())
			.budget(request.getBudget())
			.url(request.getUrl())
			.comment(request.getComment())
			.requestId(request.getRequestId())
			.build();
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
}