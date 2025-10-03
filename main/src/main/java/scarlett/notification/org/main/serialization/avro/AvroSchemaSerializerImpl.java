package scarlett.notification.org.main.serialization.avro;

import lombok.RequiredArgsConstructor;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.avro.AuroQueuePayload;
import scarlett.notification.org.main.serialization.SchemaSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AvroSchemaSerializerImpl implements SchemaSerializer {
    private final AvroBaseSchemaMapper mapper;
    @Override
    public byte[] serializePayload(QueuePayload payload) {
        try {
            AuroQueuePayload map = mapper.map(payload);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DatumWriter<AuroQueuePayload> writer = new SpecificDatumWriter<>(AuroQueuePayload.class);
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

            writer.write(map, encoder);
            encoder.flush();

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
