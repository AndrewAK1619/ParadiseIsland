package pl.example.components.offer.location.region;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class RegionService {

	private RegionRepository regionRepository;

	public RegionService(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	}

	public List<String> findAllNames() {
        return regionRepository.findAll()
                .stream()
                .map(Region::getName)
                .collect(Collectors.toList());
    }
}
