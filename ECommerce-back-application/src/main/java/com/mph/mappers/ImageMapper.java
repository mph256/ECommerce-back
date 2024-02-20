package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Image;

import com.mph.dto.ImageDto;

public class ImageMapper {

	public static ImageDto convertToDto(Image image) {
		return ImageDto.of(image.getId(), image.getFile());
	}

	public static List<ImageDto> convertToDto(List<Image> images) {

		List<ImageDto> list = new ArrayList<ImageDto>();

		for(Image image: images) {
			list.add(convertToDto(image));
		}

		return list;

	}

}