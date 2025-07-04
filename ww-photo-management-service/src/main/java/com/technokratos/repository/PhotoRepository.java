package com.technokratos.repository;

import com.technokratos.entity.FileMetadata;
import com.technokratos.util.db.SqlParameters;
import com.technokratos.util.db.SqlQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void save(FileMetadata fileMetadata) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(SqlParameters.Photo.FILE_ID, fileMetadata.getFileId())
                .addValue(SqlParameters.Photo.OWNER_ID, fileMetadata.getOwnerId())
                .addValue(SqlParameters.Photo.FILENAME, fileMetadata.getFilename())
                .addValue(SqlParameters.Photo.EXTENSION, fileMetadata.getExtension())
                .addValue(SqlParameters.Photo.FILE_TYPE, fileMetadata.getFileType())
                .addValue(SqlParameters.Photo.SIZE, fileMetadata.getSize())
                .addValue(SqlParameters.Photo.UPLOAD_DATE, Timestamp.valueOf(fileMetadata.getUploadDateTime()));

        jdbcTemplate.update(
                SqlQuery.Photo.SAVE,
                parameters
        );
    }
}
