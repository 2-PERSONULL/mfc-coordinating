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

	@Override
	@Transactional
	public Long createCoordinates(CoordinatesRequest request) {
		Coordinates coordinates = Coordinates.builder()
			.category(request.getCategory())
			.brand(request.getBrand())
			.budget(request.getBudget())
			.url(request.getUrl())
			.comment(request.getComment())
			.requestId(request.getRequestId())
			.build();

		Coordinates savedCoordinates = coordinatesRepository.save(coordinates);

		List<CoordinatesImage> images = request.getImages().stream()
			.map(imageUrl -> CoordinatesImage.builder()
				.imageUrl(imageUrl)
				.coordinates(savedCoordinates)
				.build())
			.collect(Collectors.toList());

		coordinatesImageRepository.saveAll(images);

		return savedCoordinates.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public CoordinatesResponse getCoordinatesById(Long id) {
		Coordinates coordinates = coordinatesRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND));

		List<CoordinatesImage> coordinatesImages = coordinatesImageRepository.findByCoordinatesId(id);

		return CoordinatesResponse.from(coordinates, coordinatesImages);
	}

	@Override
	@Transactional
	public void updateCoordinates(Long id, CoordinatesRequest request) {
		Coordinates coordinates = coordinatesRepository.findById(id)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.COORDINATES_NOT_FOUND));

		coordinates.update(
			request.getCategory(),
			request.getBrand(),
			request.getBudget(),
			request.getUrl(),
			request.getComment()
		);

		coordinatesImageRepository.deleteByCoordinatesId(coordinates.getId());

		List<CoordinatesImage> images = request.getImages().stream()
			.map(imageUrl -> CoordinatesImage.builder()
				.imageUrl(imageUrl)
				.coordinates(coordinates)
				.build())
			.collect(Collectors.toList());

		coordinatesImageRepository.saveAll(images);
	}

	@Override
	@Transactional
	public void deleteCoordinates(Long id) {
		coordinatesRepository.deleteById(id);
	}
}