package com.ivzb.encrypted_chat.conversation.data;

import com.ivzb.encrypted_chat._base.data._contracts.generators.BaseGeneratorConfig;
import com.ivzb.encrypted_chat._base.data.generators.DefaultGeneratorConfig;
import com.ivzb.encrypted_chat._base.data.sources.mocks.MockSaveDataSource;

public class MessagesMockDataSource
        extends MockSaveDataSource<MessageEntity>
        implements MessagesDataSource {

    private static MessagesDataSource sINSTANCE;
    private BaseGeneratorConfig mGeneratorConfig;

    public static MessagesDataSource getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new MessagesMockDataSource();
        }

        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    private MessagesMockDataSource() {
        super(new MessagesGenerator(DefaultGeneratorConfig.getInstance()));

        mGeneratorConfig = DefaultGeneratorConfig.getInstance();
    }
}