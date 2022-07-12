import { Observable } from 'rxjs';
import { AlticciModel } from '../models/alticci.model';
import { SingleResultModel } from '../utils/responses/single-result.model';

export abstract class AlticciRepository {
  abstract calculateAlticciUsecase(value: number): Observable<SingleResultModel<AlticciModel>>;
}
