package com.culturemoa.cultureMoaProject.chat.service;

import com.culturemoa.cultureMoaProject.chat.dto.RoomReadDTO;
import com.culturemoa.cultureMoaProject.chat.repository.RoomReadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class RoomReadService {
    private final RoomReadRepository repository;

    public RoomReadService(RoomReadRepository repository) {
        this.repository = repository;
    }

    public void saveRoomRead(Long user_idx, Long room_idx){
        RoomReadDTO roomReadDTO = new RoomReadDTO(user_idx, room_idx);
        repository.save(roomReadDTO);
    }

    public void updateRoomRead(Long user_idx, Long room_idx){
        Optional<RoomReadDTO> statusOpt = repository.findById(user_idx.toString()+"_"+room_idx.toString());

        if (statusOpt.isPresent()) {
            RoomReadDTO status = statusOpt.get();
            status.setLastReadAt(Instant.now());
            repository.save(status);
        }
    }
}
