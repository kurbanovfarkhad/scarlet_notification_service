package scarlett.notification.org.main.service.serialization.avro;

import lombok.RequiredArgsConstructor;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.avro.AuroQueuePayload;
import scarlett.notification.org.main.service.serialization.SchemaDeserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AvroSchemaDeserializerImpl implements SchemaDeserializer {

    private final AvroBaseSchemaMapper mapper;

    public QueuePayload deserializePayload(byte[] payload) {
        try {
            DatumReader<AuroQueuePayload> datumReader = new SpecificDatumReader<>(AuroQueuePayload.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(payload), null);
            AuroQueuePayload read = datumReader.read(null, decoder);

            return mapper.map(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
