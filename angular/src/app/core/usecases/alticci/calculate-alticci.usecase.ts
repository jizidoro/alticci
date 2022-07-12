import { Injectable } from '@angular/core';
import { UseCase } from '../../utils/bases/use-case';
import { Observable } from 'rxjs';
import { AlticciModel } from '../../models/alticci.model';
import { AlticciRepository } from '../../repositories/alticci.repository';
import { SingleResultModel } from '../../utils/responses/single-result.model';

@Injectable({
  providedIn: 'root',
})
export class CalculateAlticciUsecase implements UseCase<number, SingleResultModel<AlticciModel>> {
  constructor(private alticciRepository: AlticciRepository) {}

  execute(value: number): Observable<SingleResultModel<AlticciModel>> {
    return this.alticciRepository.calculateAlticciUsecase(value);
  }
}
