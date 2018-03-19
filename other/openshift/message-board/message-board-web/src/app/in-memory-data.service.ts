import {InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const users = [
      {id: 1, userName: 'fercoli'},
      {id: 2, userName: 'aboriero'}
    ];

//    const login = {'id': 1, 'userName': 'fercoli'};
    return {users};
  }
}
