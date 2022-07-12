import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AlticciWebRepositoryMapper as AlticciWebRepositoryMapper } from './alticci-web-repository-mapper';
import { AlticciWebEntity } from './alticci-web-entity';
import { map } from 'rxjs/operators';
import { BaseHttpService } from 'src/app/services/http/base-http.service';
import { environment } from 'src/environments/environment';
import { AlticciRepository } from 'src/app/core/repositories/alticci.repository';
import { AlticciModel } from 'src/app/core/models/alticci.model';
import { PageResultModel } from 'src/app/core/utils/responses/page-result.model';
import { PageFilterModel } from 'src/app/core/utils/filters/page-filter.model';
import { makeParamFilterGrid } from '../../helper.repository';
import { SingleResultModel } from '../../../core/utils/responses/single-result.model';

@Injectable({
  providedIn: 'root',
})
export class AlticciWebRepository extends AlticciRepository {
  mapper = new AlticciWebRepositoryMapper();

  constructor(public http: BaseHttpService) {
    super();
  }

  calculateAlticciUsecase(value: number): Observable<SingleResultModel<AlticciModel>> {
    PageResultModel;
    return this.http
      .get<SingleResultModel<AlticciWebEntity>>(`${environment.ALTICCI}alticci`, value.toString())
      .pipe(map((x) => this.mapper.responseWebMapFrom(x.data)));
  }
}
