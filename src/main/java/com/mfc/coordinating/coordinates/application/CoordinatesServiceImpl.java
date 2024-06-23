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

		//String requestId = requests.get(0).getRequestId();
		//kafkaTemplate.send("coordinates-submitted", String.valueOf(requestId));

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
			.map(coordinates -> {
				List<CoordinatesImage> images = coordinatesImageRepository.findByCoordinatesId(coordinates.getId());
				return CoordinatesResponse.from(coordinates, images);
			})
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateCoordinates(Long coordinateId, CoordinatesRequest request) {
		Coordinates coordinates = coordinatesRepository.findById(coordinateId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND));

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