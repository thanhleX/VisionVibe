import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const myStompConfig: InjectableRxStompConfig = {
  brokerURL: 'ws://localhost:8080/ws-notifications/websocket',

  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 500,

  debug: (msg: string): void => {
  },
};