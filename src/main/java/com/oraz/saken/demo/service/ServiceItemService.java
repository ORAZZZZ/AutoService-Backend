package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.service_item.ServiceItemRequest;
import com.oraz.saken.demo.dto.service_item.ServiceItemResponse;
import com.oraz.saken.demo.entity.ServiceItem;
import com.oraz.saken.demo.exception.NotFoundException;
import com.oraz.saken.demo.repository.ServiceItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceItemService {
    private final ServiceItemRepository serviceItemRepositorye;

    public ServiceItemService(ServiceItemRepository serviceItemRepositorye) {
        this.serviceItemRepositorye = serviceItemRepositorye;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "serviceItems", key = "#query == null ? 'all' : #query")
    public List<ServiceItemResponse> getAll(String query) {
        return serviceItemRepositorye.findByQuery(query).stream()
                .map(serviceItem -> new ServiceItemResponse(serviceItem.getId(), serviceItem.getName(), serviceItem.getDescription(), serviceItem.getPrice(), serviceItem.getDurationInMinutes()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "serviceItemById", key = "#id")
    public ServiceItemResponse getById(Long id) {
        ServiceItem serviceItem = serviceItemRepositorye.findById(id)
                .orElseThrow(() -> new NotFoundException("Услуга не найдена"));

        return new ServiceItemResponse(serviceItem.getId(), serviceItem.getName(), serviceItem.getDescription(), serviceItem.getPrice(), serviceItem.getDurationInMinutes());
    }

    @CacheEvict(value = {"serviceItems", "serviceItemById"}, allEntries = true)
    public ServiceItemResponse create(ServiceItemRequest request) {
        ServiceItem serviceItem = new ServiceItem();

        serviceItem.setName(request.getName());
        serviceItem.setDescription(request.getDescription());
        serviceItem.setPrice(request.getPrice());
        serviceItem.setDurationInMinutes(request.getDurationInMinutes());

        serviceItemRepositorye.save(serviceItem);

        return new ServiceItemResponse(serviceItem.getId(), serviceItem.getName(), serviceItem.getDescription(), serviceItem.getPrice(), serviceItem.getDurationInMinutes());
    }

    @CacheEvict(value = {"serviceItems", "serviceItemById"}, allEntries = true)
    public ServiceItemResponse update(Long id, ServiceItemRequest request) {
        ServiceItem serviceItem = serviceItemRepositorye.findById(id)
                .orElseThrow(() -> new NotFoundException("Услуга не найдена"));

        serviceItem.setName(request.getName());
        serviceItem.setDescription(request.getDescription());
        serviceItem.setPrice(request.getPrice());
        serviceItem.setDurationInMinutes(request.getDurationInMinutes());

        serviceItemRepositorye.save(serviceItem);

        return new ServiceItemResponse(serviceItem.getId(), serviceItem.getName(), serviceItem.getDescription(), serviceItem.getPrice(), serviceItem.getDurationInMinutes());
    }

    @CacheEvict(value = {"serviceItems", "serviceItemById"}, allEntries = true)
    public void delete(Long id) {
        if (!serviceItemRepositorye.existsById(id)) {
            throw new NotFoundException("Услуга не найдена");
        }

        serviceItemRepositorye.deleteById(id);
    }
}
