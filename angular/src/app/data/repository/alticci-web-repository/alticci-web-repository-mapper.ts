import { Mapper } from '../../../core/utils/bases/mapper';
import { AlticciWebEntity } from './alticci-web-entity';
import { AlticciModel } from 'src/app/core/models/alticci.model';

export class AlticciWebRepositoryMapper extends Mapper<AlticciWebEntity, AlticciModel> {
  mapFrom(param: AlticciWebEntity): AlticciModel {
    return {
      calculatedValue: param.calculatedValue,
    };
  }

  mapTo(param: AlticciModel): AlticciWebEntity {
    return {
      calculatedValue: param.calculatedValue,
    };
  }
}
